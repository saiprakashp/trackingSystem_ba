CREATE DEFINER=`ptsusr`@`%` PROCEDURE `pts`.`CALICULATE_EFFORT`()
BEGIN

END;

CREATE DEFINER=`ptsusr`@`%` PROCEDURE `pts`.`CAPACITY_SUMMARY_PROC`(seed INT, selected_year int, _status Varchar(20))
BEGIN
  Declare v_userid int;  
  Declare v_userid_list varchar(10000);
  SET SESSION group_concat_max_len=100000;
  DROP TABLE IF EXISTS temp_capacity_summary;

  CALL PTS.user_id_heirarchy_proc(seed);
  select group_concat(node SEPARATOR ', ')  from _result Into v_userid_list;

  IF _status = 'All' THEN

  set @S = concat("create temporary table temp_capacity_summary select userName, supervisorName, ",
  "(select count(*) from  PTS_USER u, PTS_USER_SUPERVISOR us where u.ID=us.user_id  and ",
  "status not in ('OffBoard', 'No Show', 'Deleted', 'Others', 'Notice Period') and  u.id in (",v_userid_list,")) headCount, ",
  "ROUND(sum(janCapacity), 2) janCapacity, ROUND(sum(febCapacity), 2) febCapacity, ROUND(sum(marCapacity), 2) marCapacity, ",
  "ROUND(sum(aprCapacity), 2) aprCapacity, ROUND(sum(mayCapacity), 2) mayCapacity, ROUND(sum(junCapacity), 2) junCapacity, ",
  "ROUND(sum(julCapacity), 2) julCapacity, ROUND(sum(augCapacity), 2) augCapacity, ROUND(sum(sepCapacity), 2) sepCapacity, ",
  "ROUND(sum(octCapacity), 2) octCapacity, ROUND(sum(novCapacity), 2) novCapacity, ROUND(sum(decCapacity), 2) decCapacity  ",
  "from (select (select name from PTS.PTS_USER where id in (",seed,")) userName,  (select name from PTS.PTS_USER where ",
  "id in (select distinct tus.supervisor_id from PTS.PTS_USER_SUPERVISOR tus where tus.user_id=",seed,")) supervisorName, ",
  "sum(cp.JAN_CPTY) 'janCapacity', sum(cp.FEB_CPTY) 'febCapacity', sum(cp.MAR_CPTY) 'marCapacity', sum(cp.APR_CPTY) 'aprCapacity',  ",
  "sum(cp.MAY_CPTY) 'mayCapacity', sum(cp.JUN_CPTY) 'junCapacity', sum(cp.JUL_CPTY) 'julCapacity', sum(cp.AUG_CPTY) 'augCapacity',  ",
  "sum(cp.SEP_CPTY) 'sepCapacity', sum(cp.OCT_CPTY) 'octCapacity',  sum(cp.NOV_CPTY) 'novCapacity', sum(cp.DEC_CPTY) 'decCapacity'  ",
  " from PTS_USER_CAPACITY_PLANNING cp, PTS_USER u where cp.year=",selected_year," and cp.user_id=u.id and cp.user_id in (",v_userid_list,
  ")  group by cp.user_id ) t group by userName order by supervisorName, userName");

  PREPARE STMT from @S;
  EXECUTE STMT;

  ELSE

  set @S = concat("create temporary table temp_capacity_summary select userName, supervisorName, ",
  "(select count(*) from  PTS_USER u, PTS_USER_SUPERVISOR us where u.ID=us.user_id  and ",
  "status not in ('OffBoard', 'No Show', 'Deleted', 'Others', 'Notice Period') and  u.id in (",v_userid_list,")) headCount, ",
  "ROUND(sum(janCapacity), 2) janCapacity, ROUND(sum(febCapacity), 2) febCapacity, ROUND(sum(marCapacity), 2) marCapacity, ",
  "ROUND(sum(aprCapacity), 2) aprCapacity, ROUND(sum(mayCapacity), 2) mayCapacity, ROUND(sum(junCapacity), 2) junCapacity, ",
  "ROUND(sum(julCapacity), 2) julCapacity, ROUND(sum(augCapacity), 2) augCapacity, ROUND(sum(sepCapacity), 2) sepCapacity, ",
  "ROUND(sum(octCapacity), 2) octCapacity, ROUND(sum(novCapacity), 2) novCapacity, ROUND(sum(decCapacity), 2) decCapacity ",
  "from (select (select name from PTS.PTS_USER where id in (",seed,")) userName,  (select name from PTS.PTS_USER where ",
  "id in (select distinct tus.supervisor_id from PTS.PTS_USER_SUPERVISOR tus where tus.user_id=",seed,")) supervisorName, ",
  "sum(cp.JAN_CPTY) 'janCapacity', sum(cp.FEB_CPTY) 'febCapacity',  sum(cp.MAR_CPTY) 'marCapacity', sum(cp.APR_CPTY) 'aprCapacity',  ",
  "sum(cp.MAY_CPTY) 'mayCapacity', sum(cp.JUN_CPTY) 'junCapacity',  sum(cp.JUL_CPTY) 'julCapacity', sum(cp.AUG_CPTY) 'augCapacity',  ",
  "sum(cp.SEP_CPTY) 'sepCapacity', sum(cp.OCT_CPTY) 'octCapacity',  sum(cp.NOV_CPTY) 'novCapacity', sum(cp.DEC_CPTY) 'decCapacity'  ",
  "from PTS_USER_CAPACITY_PLANNING cp, PTS_USER u where cp.year=",selected_year," and cp.user_id=u.id and cp.user_id in (",v_userid_list,
  ") and u.status='",_status,"' group by cp.user_id ) t group by userName order by supervisorName, userName");

  PREPARE STMT from @S;
  EXECUTE STMT;

  END IF;

  DROP TABLE IF EXISTS tmp_result;
  CREATE  TABLE tmp_result (node INT PRIMARY KEY, valid bit(1) not null);


  set @S = concat("INSERT  INTO tmp_result(node, valid) select distinct us.supervisor_id, 0 from PTS_USER u, PTS_USER_SUPERVISOR us where ",
  "u.id <> 1 and us.supervisor_id IN (",v_userid_list,")  and us.supervisor_id <> ",seed," and u.status not in ('No Show', 'Open','Selected', 'Deleted')");

  PREPARE STMT from @S;
  EXECUTE STMT;  

	While exists(Select node From tmp_result Where valid = 0) Do 


    Select node Into @v_userid From tmp_result Where valid = 0 Limit 1; 



 CALL PTS.user_id_heirarchy_proc(@v_userid);
 select group_concat(node SEPARATOR ', ')  from _result Into v_userid_list;

 IF _status = 'All' THEN

 set @S = concat("insert into temp_capacity_summary select userName, supervisorName, ",
 "(select count(*) from  PTS_USER u, PTS_USER_SUPERVISOR us where u.ID=us.user_id  ",
 "and status not in ('OffBoard', 'No Show', 'Deleted', 'Others', 'Notice Period') and  u.id in (",v_userid_list,")) headCount, ",
 "ROUND(sum(janCapacity), 2) janCapacity, ROUND(sum(febCapacity), 2) febCapacity, ROUND(sum(marCapacity), 2) marCapacity, ",
 "ROUND(sum(aprCapacity), 2) aprCapacity, ROUND(sum(mayCapacity), 2) mayCapacity, ROUND(sum(junCapacity), 2) junCapacity, ",
 "ROUND(sum(julCapacity), 2) julCapacity, ROUND(sum(augCapacity), 2) augCapacity, ROUND(sum(sepCapacity), 2) sepCapacity, ",
 "ROUND(sum(octCapacity), 2) octCapacity, ROUND(sum(novCapacity), 2) novCapacity, ROUND(sum(decCapacity), 2) decCapacity ",
 "from (select (select name from PTS.PTS_USER where id in (",@v_userid,")) userName, (select name from PTS.PTS_USER where ",
 "id in (select distinct tus.supervisor_id from PTS.PTS_USER_SUPERVISOR tus where tus.user_id=",@v_userid,")) supervisorName,  ",
 "sum(cp.JAN_CPTY) 'janCapacity', sum(cp.FEB_CPTY) 'febCapacity',  sum(cp.MAR_CPTY) 'marCapacity', sum(cp.APR_CPTY) 'aprCapacity',  ",
 "sum(cp.MAY_CPTY) 'mayCapacity', sum(cp.JUN_CPTY) 'junCapacity',  sum(cp.JUL_CPTY) 'julCapacity', sum(cp.AUG_CPTY) 'augCapacity',  ",
 "sum(cp.SEP_CPTY) 'sepCapacity', sum(cp.OCT_CPTY) 'octCapacity',  sum(cp.NOV_CPTY) 'novCapacity', sum(cp.DEC_CPTY) 'decCapacity'  ",
 "from PTS_USER_CAPACITY_PLANNING cp, PTS_USER u where cp.year=",selected_year," and cp.user_id=u.id and cp.user_id in (",v_userid_list,
 ")  group by cp.user_id ) t group by userName order by supervisorName, userName");

  PREPARE STMT from @S;
  EXECUTE STMT;

  ELSE

  set @S = concat("insert into temp_capacity_summary select userName, supervisorName, ",
  "(select count(*) from  PTS_USER u, PTS_USER_SUPERVISOR us where u.ID=us.user_id  ",
  "and status not in ('OffBoard', 'No Show', 'Deleted', 'Others', 'Notice Period') and  u.id in (",v_userid_list,")) headCount, ",
  "ROUND(sum(janCapacity), 2) janCapacity, ROUND(sum(febCapacity), 2) febCapacity, ROUND(sum(marCapacity), 2) marCapacity, ",
  "ROUND(sum(aprCapacity), 2) aprCapacity, ROUND(sum(mayCapacity), 2) mayCapacity, ROUND(sum(junCapacity), 2) junCapacity, ",
  "ROUND(sum(julCapacity), 2) julCapacity, ROUND(sum(augCapacity), 2) augCapacity, ROUND(sum(sepCapacity), 2) sepCapacity, ",
  "ROUND(sum(octCapacity), 2) octCapacity, ROUND(sum(novCapacity), 2) novCapacity, ROUND(sum(decCapacity), 2) decCapacity ",
  "from (select (select name from PTS.PTS_USER where id in (",@v_userid,")) userName, (select name from PTS.PTS_USER where ",
  "id in (select distinct tus.supervisor_id from PTS.PTS_USER_SUPERVISOR tus where tus.user_id=",@v_userid,")) supervisorName,  ",
  " sum(cp.JAN_CPTY) 'janCapacity', sum(cp.FEB_CPTY) 'febCapacity',  sum(cp.MAR_CPTY) 'marCapacity', sum(cp.APR_CPTY) 'aprCapacity',  ",
  "sum(cp.MAY_CPTY) 'mayCapacity', sum(cp.JUN_CPTY) 'junCapacity',   sum(cp.JUL_CPTY) 'julCapacity', sum(cp.AUG_CPTY) 'augCapacity',  ",
  "sum(cp.SEP_CPTY) 'sepCapacity', sum(cp.OCT_CPTY) 'octCapacity',  sum(cp.NOV_CPTY) 'novCapacity', sum(cp.DEC_CPTY) 'decCapacity'  ",
  "from PTS_USER_CAPACITY_PLANNING cp, PTS_USER u where cp.year=",selected_year," and cp.user_id=u.id and cp.user_id in (",v_userid_list,
  ") and u.status='",_status,"' group by cp.user_id ) t group by userName order by supervisorName, userName");

  PREPARE STMT from @S;
  EXECUTE STMT;

  END IF;

 Update tmp_result 
    Set valid = 1 
     Where node = @v_userid; 


END WHILE; 

 DROP TABLE tmp_result;

END;

CREATE DEFINER=`ptsusr`@`%` PROCEDURE `pts`.`CAPACITY_SUMMARY_PROC_NEW`(seed INT, selected_year int, _status Varchar(20), _platform_id int, _project_id int, _technology_id int, _region Varchar(20), _stream_id int)
BEGIN
  Declare v_userid int;  
  Declare v_userid_list varchar(10000);
  Declare v_final_userid_list varchar(10000);
  
  Declare v_target_hours FLOAT DEFAULT 0.00;
  SET SESSION group_concat_max_len=100000;
  DROP TABLE IF EXISTS temp_capacity_summary;


  CALL PTS.user_id_heirarchy_proc(seed);
  select group_concat(node SEPARATOR ', ')  from _result Into v_userid_list;
  set @v_final_userid_list = '';
  set @temp_user_id = concat ("select group_concat(t.id SEPARATOR ', ') from (select u.id  from PTS_USER u, PTS_LOCATION l ");

    
  IF _platform_id <> 0 THEN
	set @temp_user_id = concat(@temp_user_id, ", PTS_USER_PLATFORMS up");
  END IF;

  IF _project_id <> 0 THEN
	IF _platform_id <> 0 THEN
		set @temp_user_id = concat(@temp_user_id, ", PTS_USER_APPS ua");
	ELSE
		set @temp_user_id = concat(@temp_user_id, ", PTS_PROJECT pro, PTS_USER_APPS ua, PTS_USER_PLATFORMS up");
	END IF;
  END IF;

  IF _technology_id <> 0 THEN
	set @temp_user_id = concat(@temp_user_id, ", PTS_USER_SKILLS us");
  END IF;

  set @temp_user_id = concat(@temp_user_id, " where u.id in (",v_userid_list,")" );
  set @temp_user_id = concat(@temp_user_id, " and u.location_id=l.id ");

  IF _status != 'ALL' THEN
     set @temp_user_id = concat(@temp_user_id, " and u.status='",_status,"'");
  END IF;
  IF _stream_id <> 0 THEN
	set @temp_user_id = concat(@temp_user_id, " and u.stream=",_stream_id);
  END IF;
  IF _region != 'ALL' THEN
	set @temp_user_id = concat(@temp_user_id, " and l.region='",_region,"'");
  END IF;

  IF _platform_id <> 0 THEN
	set @temp_user_id = concat(@temp_user_id, " and u.id=up.user_id and up.contribution > 0 and up.platform_id=", _platform_id);
  END IF;

  IF _project_id <> 0 THEN
	IF _platform_id <> 0 THEN
		set @temp_user_id = concat(@temp_user_id, " and u.id=ua.user_id and ua.project_id=",_project_id);
	ELSE
		set @temp_user_id = concat(@temp_user_id, " and u.id=ua.user_id and ua.project_id=",_project_id, " and up.contribution > 0 and up.platform_id=pro.pillar_id and pro.id=ua.project_id and up.user_id=ua.user_id ");
	END IF;
  END IF;

  IF _technology_id <> 0 THEN
	set @temp_user_id = concat(@temp_user_id, " and u.id=us.user_id and us.primary_flag='Y' and us.technology_id=",_technology_id);
  END IF;

  set @temp_user_id = concat(@temp_user_id, " group by u.id) t into @v_final_userid_list");
  
  PREPARE STMT from @temp_user_id;
  EXECUTE STMT;
  DEALLOCATE PREPARE STMT;
  
  if(@v_final_userid_list is not null) THEN
  set @target_hours_qry = concat(" select  up.contribution, up.user_id from PTS_USER_PLATFORMS up ");
  IF _platform_id <> 0 THEN
	set @target_hours_qry = concat(@target_hours_qry, " where up.contribution >0 and up.user_id in (",@v_final_userid_list,") ");
	set @target_hours_qry = concat(@target_hours_qry, " and up.platform_id=", _platform_id);
  ELSE 
	IF _project_id <> 0 THEN
		set @target_hours_qry = concat(@target_hours_qry, ", PTS_PROJECT pro, PTS_USER_APPS ua ");
		set @target_hours_qry = concat(@target_hours_qry, " where up.contribution >0 and up.user_id in (",@v_final_userid_list,") and up.platform_id=pro.pillar_id and pro.id=ua.project_id and up.user_id=ua.user_id ");
		set @target_hours_qry = concat(@target_hours_qry, " and ua.project_id=",_project_id);
	ELSE
		set @target_hours_qry = concat(@target_hours_qry, " where up.contribution >0 and up.user_id in (",@v_final_userid_list,") ");
	END IF;
  END IF;
  
  set @target_hours_qry = concat(@target_hours_qry, " group by user_id,platform_id");
  
  set @target_hours_qry = concat("select sum(contribution)*170 from (",@target_hours_qry,") t into @v_target_hours");
  
  PREPARE STMT from @target_hours_qry;
  EXECUTE STMT;
  DEALLOCATE PREPARE STMT;
  END IF;

  DROP TABLE IF EXISTS temp_capacity_summary;
  CREATE  TABLE temp_capacity_summary (userName VARCHAR(100) PRIMARY KEY, supervisorName VARCHAR(100), headCount int, targetHrs FLOAT DEFAULT 0.00,janCapacity FLOAT DEFAULT 0.00, febCapacity FLOAT DEFAULT 0.00, marCapacity FLOAT DEFAULT 0.00,aprCapacity FLOAT DEFAULT 0.00, mayCapacity FLOAT DEFAULT 0.00,junCapacity FLOAT DEFAULT 0.00, julCapacity FLOAT DEFAULT 0.00,augCapacity FLOAT DEFAULT 0.00,sepCapacity FLOAT DEFAULT 0.00,octCapacity FLOAT DEFAULT 0.00,novCapacity FLOAT DEFAULT 0.00,decCapacity FLOAT DEFAULT 0.00, createdDate TIMESTAMP(6) );

  IF _status = 'All' THEN
if(@v_final_userid_list is not null) THEN
  set @S = concat("insert into temp_capacity_summary  select userName, supervisorName, ",
  "(select count(*) from  PTS_USER u, PTS_USER_SUPERVISOR us where u.ID=us.user_id  and ",
  "u.id in (",@v_final_userid_list,")) headCount,",@v_target_hours," targetHrs , ",
  "ROUND(sum(janCapacity), 2) janCapacity, ROUND(sum(febCapacity), 2) febCapacity, ROUND(sum(marCapacity), 2) marCapacity, ",
  "ROUND(sum(aprCapacity), 2) aprCapacity, ROUND(sum(mayCapacity), 2) mayCapacity, ROUND(sum(junCapacity), 2) junCapacity, ",
  "ROUND(sum(julCapacity), 2) julCapacity, ROUND(sum(augCapacity), 2) augCapacity, ROUND(sum(sepCapacity), 2) sepCapacity, ",
  "ROUND(sum(octCapacity), 2) octCapacity, ROUND(sum(novCapacity), 2) novCapacity, ROUND(sum(decCapacity), 2) decCapacity, sysdate(6)  ",
  "from (select (select name from PTS.PTS_USER where id in (",seed,")) userName,  (select name from PTS.PTS_USER where ",
  "id in (select distinct tus.supervisor_id from PTS.PTS_USER_SUPERVISOR tus where tus.user_id=",seed,")) supervisorName, ",
  "sum(cp.JAN_CPTY) 'janCapacity', sum(cp.FEB_CPTY) 'febCapacity', sum(cp.MAR_CPTY) 'marCapacity', sum(cp.APR_CPTY) 'aprCapacity',  ",
  "sum(cp.MAY_CPTY) 'mayCapacity', sum(cp.JUN_CPTY) 'junCapacity', sum(cp.JUL_CPTY) 'julCapacity', sum(cp.AUG_CPTY) 'augCapacity',  ",
  "sum(cp.SEP_CPTY) 'sepCapacity', sum(cp.OCT_CPTY) 'octCapacity',  sum(cp.NOV_CPTY) 'novCapacity', sum(cp.DEC_CPTY) 'decCapacity'  ",
  " from PTS_USER_CAPACITY_PLANNING cp, PTS_USER u where cp.year=",selected_year," and cp.user_id=u.id and cp.user_id in (",@v_final_userid_list,
  ")  group by cp.user_id ) t group by userName order by supervisorName, userName");

  PREPARE STMT from @S;
  EXECUTE STMT;
  DEALLOCATE PREPARE STMT;
 END IF;
  ELSE
if(@v_final_userid_list is not null) THEN
  set @S = concat("insert into temp_capacity_summary  select userName, supervisorName, ",
  "(select count(*) from  PTS_USER u, PTS_USER_SUPERVISOR us where u.ID=us.user_id  and ",
  "u.id in (",@v_final_userid_list,")) headCount,",@v_target_hours," targetHrs , ",
  "ROUND(sum(janCapacity), 2) janCapacity, ROUND(sum(febCapacity), 2) febCapacity, ROUND(sum(marCapacity), 2) marCapacity, ",
  "ROUND(sum(aprCapacity), 2) aprCapacity, ROUND(sum(mayCapacity), 2) mayCapacity, ROUND(sum(junCapacity), 2) junCapacity, ",
  "ROUND(sum(julCapacity), 2) julCapacity, ROUND(sum(augCapacity), 2) augCapacity, ROUND(sum(sepCapacity), 2) sepCapacity, ",
  "ROUND(sum(octCapacity), 2) octCapacity, ROUND(sum(novCapacity), 2) novCapacity, ROUND(sum(decCapacity), 2) decCapacity, sysdate(6) ",
  "from (select (select name from PTS.PTS_USER where id in (",seed,")) userName,  (select name from PTS.PTS_USER where ",
  "id in (select distinct tus.supervisor_id from PTS.PTS_USER_SUPERVISOR tus where tus.user_id=",seed,")) supervisorName, ",
  "sum(cp.JAN_CPTY) 'janCapacity', sum(cp.FEB_CPTY) 'febCapacity',  sum(cp.MAR_CPTY) 'marCapacity', sum(cp.APR_CPTY) 'aprCapacity',  ",
  "sum(cp.MAY_CPTY) 'mayCapacity', sum(cp.JUN_CPTY) 'junCapacity',  sum(cp.JUL_CPTY) 'julCapacity', sum(cp.AUG_CPTY) 'augCapacity',  ",
  "sum(cp.SEP_CPTY) 'sepCapacity', sum(cp.OCT_CPTY) 'octCapacity',  sum(cp.NOV_CPTY) 'novCapacity', sum(cp.DEC_CPTY) 'decCapacity'  ",
  "from PTS_USER_CAPACITY_PLANNING cp, PTS_USER u where cp.year=",selected_year," and cp.user_id=u.id and cp.user_id in (",@v_final_userid_list,
  ") and u.status='",_status,"' group by cp.user_id ) t group by userName order by supervisorName, userName");

  PREPARE STMT from @S;
  EXECUTE STMT;
  DEALLOCATE PREPARE STMT;
 END IF;
  END IF;

  DROP TABLE IF EXISTS tmp_result;
  CREATE  TABLE tmp_result (node INT PRIMARY KEY, valid bit(1) not null);


  set @S = concat("INSERT  INTO tmp_result(node, valid) select distinct us.supervisor_id, 0 from PTS_USER u, PTS_USER_SUPERVISOR us where ",
  "u.id <> 1 and us.supervisor_id IN (",v_userid_list,")  and us.supervisor_id <> ",seed," and u.status not in ('No Show', 'Open','Selected', 'Deleted')");

  PREPARE STMT from @S;
  EXECUTE STMT; 
  DEALLOCATE PREPARE STMT;  

	While exists(Select node From tmp_result Where valid = 0) Do 


    Select node Into @v_userid From tmp_result Where valid = 0 Limit 1; 



 CALL PTS.user_id_heirarchy_proc(@v_userid);
 select group_concat(node SEPARATOR ', ')  from _result Into v_userid_list;

  set @v_final_userid_list = '';
  set @temp_user_id = '';
  set @temp_user_id = concat ("select group_concat(t.id SEPARATOR ', ') from (select u.id  from PTS_USER u, PTS_LOCATION l ");

  IF _platform_id <> 0 THEN
	set @temp_user_id = concat(@temp_user_id, ", PTS_USER_PLATFORMS up");
  END IF;

  IF _project_id <> 0 THEN
	IF _platform_id <> 0 THEN
		set @temp_user_id = concat(@temp_user_id, ", PTS_USER_APPS ua");
	ELSE
		set @temp_user_id = concat(@temp_user_id, ", PTS_PROJECT pro, PTS_USER_APPS ua, PTS_USER_PLATFORMS up");
	END IF;
  END IF;

  IF _technology_id <> 0 THEN
	set @temp_user_id = concat(@temp_user_id, ", PTS_USER_SKILLS us");
  END IF;

  set @temp_user_id = concat(@temp_user_id, " where u.id in (",v_userid_list,")");
  set @temp_user_id = concat(@temp_user_id, " and u.location_id=l.id ");

  IF _status != 'ALL' THEN
     set @temp_user_id = concat(@temp_user_id, " and u.status='",_status,"'");
  END IF;
  IF _stream_id <> 0 THEN
	set @temp_user_id = concat(@temp_user_id, " and u.stream=",_stream_id);
  END IF;
  IF _region != 'ALL' THEN
	set @temp_user_id = concat(@temp_user_id, " and l.region='",_region,"'");
  END IF;

  IF _platform_id <> 0 THEN
	set @temp_user_id = concat(@temp_user_id, " and u.id=up.user_id and up.contribution > 0 and up.platform_id=", _platform_id);
  END IF;

  IF _project_id <> 0 THEN
	IF _platform_id <> 0 THEN
		set @temp_user_id = concat(@temp_user_id, " and u.id=ua.user_id and ua.project_id=",_project_id);
	ELSE
		set @temp_user_id = concat(@temp_user_id, " and u.id=ua.user_id and ua.project_id=",_project_id, " and up.contribution > 0 and up.platform_id=pro.pillar_id and pro.id=ua.project_id and up.user_id=ua.user_id ");
	END IF;
  END IF;

  IF _technology_id <> 0 THEN
	set @temp_user_id = concat(@temp_user_id, " and u.id=us.user_id and us.primary_flag='Y' and us.technology_id=",_technology_id);
  END IF;

  set @temp_user_id = concat(@temp_user_id, " group by u.id) t into @v_final_userid_list");

  PREPARE STMT from @temp_user_id;
  EXECUTE STMT;
  DEALLOCATE PREPARE STMT;
  
  if(@v_final_userid_list is not null) THEN
  set @target_hours_qry = '';
  set @target_hours_qry = concat(" select  up.contribution, up.user_id from PTS_USER_PLATFORMS up ");
  IF _platform_id <> 0 THEN
	set @target_hours_qry = concat(@target_hours_qry, " where up.contribution >0 and up.user_id in (",@v_final_userid_list,") ");
	set @target_hours_qry = concat(@target_hours_qry, " and up.platform_id=", _platform_id);
  ELSE 
	IF _project_id <> 0 THEN
		set @target_hours_qry = concat(@target_hours_qry, ", PTS_PROJECT pro, PTS_USER_APPS ua ");
		set @target_hours_qry = concat(@target_hours_qry, " where up.contribution >0 and up.user_id in (",@v_final_userid_list,") and up.platform_id=pro.pillar_id and pro.id=ua.project_id and up.user_id=ua.user_id ");
		set @target_hours_qry = concat(@target_hours_qry, " and ua.project_id=",_project_id);
	ELSE
		set @target_hours_qry = concat(@target_hours_qry, " where up.contribution >0 and up.user_id in (",@v_final_userid_list,") ");
	END IF;
  END IF;
  
  set @target_hours_qry = concat(@target_hours_qry, " group by user_id,platform_id");
  
  set @target_hours_qry = concat("select sum(contribution)*170 from (",@target_hours_qry,") t into @v_target_hours");
  
  PREPARE STMT from @target_hours_qry;
  EXECUTE STMT;
  DEALLOCATE PREPARE STMT;
  END IF;

 IF _status = 'All' THEN
if(@v_final_userid_list is not null) THEN
 set @S = concat("insert into temp_capacity_summary select userName, supervisorName, ",
 "(select count(*) from  PTS_USER u, PTS_USER_SUPERVISOR us where u.ID=us.user_id  ",
 "and  u.id in (",@v_final_userid_list,")) headCount,",@v_target_hours," targetHrs , ",
 "ROUND(sum(janCapacity), 2) janCapacity, ROUND(sum(febCapacity), 2) febCapacity, ROUND(sum(marCapacity), 2) marCapacity, ",
 "ROUND(sum(aprCapacity), 2) aprCapacity, ROUND(sum(mayCapacity), 2) mayCapacity, ROUND(sum(junCapacity), 2) junCapacity, ",
 "ROUND(sum(julCapacity), 2) julCapacity, ROUND(sum(augCapacity), 2) augCapacity, ROUND(sum(sepCapacity), 2) sepCapacity, ",
 "ROUND(sum(octCapacity), 2) octCapacity, ROUND(sum(novCapacity), 2) novCapacity, ROUND(sum(decCapacity), 2) decCapacity, sysdate(6) ",
 "from (select (select name from PTS.PTS_USER where id in (",@v_userid,")) userName, (select name from PTS.PTS_USER where ",
 "id in (select distinct tus.supervisor_id from PTS.PTS_USER_SUPERVISOR tus where tus.user_id=",@v_userid,")) supervisorName,  ",
 "sum(cp.JAN_CPTY) 'janCapacity', sum(cp.FEB_CPTY) 'febCapacity',  sum(cp.MAR_CPTY) 'marCapacity', sum(cp.APR_CPTY) 'aprCapacity',  ",
 "sum(cp.MAY_CPTY) 'mayCapacity', sum(cp.JUN_CPTY) 'junCapacity',  sum(cp.JUL_CPTY) 'julCapacity', sum(cp.AUG_CPTY) 'augCapacity',  ",
 "sum(cp.SEP_CPTY) 'sepCapacity', sum(cp.OCT_CPTY) 'octCapacity',  sum(cp.NOV_CPTY) 'novCapacity', sum(cp.DEC_CPTY) 'decCapacity'  ",
 "from PTS_USER_CAPACITY_PLANNING cp, PTS_USER u where cp.year=",selected_year," and cp.user_id=u.id and cp.user_id in (",@v_final_userid_list,
 ")  group by cp.user_id ) t group by userName order by supervisorName, userName");

  PREPARE STMT from @S;
  EXECUTE STMT;
  DEALLOCATE PREPARE STMT;
END IF;
  ELSE
if(@v_final_userid_list is not null) THEN
  set @S = concat("insert into temp_capacity_summary select userName, supervisorName, ",
  "(select count(*) from  PTS_USER u, PTS_USER_SUPERVISOR us where u.ID=us.user_id  ",
  "and  u.id in (",@v_final_userid_list,")) headCount,",@v_target_hours," targetHrs , ",
  "ROUND(sum(janCapacity), 2) janCapacity, ROUND(sum(febCapacity), 2) febCapacity, ROUND(sum(marCapacity), 2) marCapacity, ",
  "ROUND(sum(aprCapacity), 2) aprCapacity, ROUND(sum(mayCapacity), 2) mayCapacity, ROUND(sum(junCapacity), 2) junCapacity, ",
  "ROUND(sum(julCapacity), 2) julCapacity, ROUND(sum(augCapacity), 2) augCapacity, ROUND(sum(sepCapacity), 2) sepCapacity, ",
  "ROUND(sum(octCapacity), 2) octCapacity, ROUND(sum(novCapacity), 2) novCapacity, ROUND(sum(decCapacity), 2) decCapacity, sysdate(6) ",
  "from (select (select name from PTS.PTS_USER where id in (",@v_userid,")) userName, (select name from PTS.PTS_USER where ",
  "id in (select distinct tus.supervisor_id from PTS.PTS_USER_SUPERVISOR tus where tus.user_id=",@v_userid,")) supervisorName,  ",
  " sum(cp.JAN_CPTY) 'janCapacity', sum(cp.FEB_CPTY) 'febCapacity',  sum(cp.MAR_CPTY) 'marCapacity', sum(cp.APR_CPTY) 'aprCapacity',  ",
  "sum(cp.MAY_CPTY) 'mayCapacity', sum(cp.JUN_CPTY) 'junCapacity',   sum(cp.JUL_CPTY) 'julCapacity', sum(cp.AUG_CPTY) 'augCapacity',  ",
  "sum(cp.SEP_CPTY) 'sepCapacity', sum(cp.OCT_CPTY) 'octCapacity',  sum(cp.NOV_CPTY) 'novCapacity', sum(cp.DEC_CPTY) 'decCapacity'  ",
  "from PTS_USER_CAPACITY_PLANNING cp, PTS_USER u where cp.year=",selected_year," and cp.user_id=u.id and cp.user_id in (",@v_final_userid_list,
  ") and u.status='",_status,"' group by cp.user_id ) t group by userName order by supervisorName, userName");

  PREPARE STMT from @S;
  EXECUTE STMT;
  DEALLOCATE PREPARE STMT;
END IF;
  END IF;

 Update tmp_result 
    Set valid = 1 
     Where node = @v_userid; 


END WHILE; 

 DROP TABLE tmp_result;

END;

CREATE DEFINER=`ptsusr`@`%` PROCEDURE `pts`.`p1`()
BEGIN
  DECLARE done INT DEFAULT FALSE;
  DECLARE t_id,t_resourceCount,t_locationId,t_userId, t_month,t_year INT ;
   DECLARE t_name,t_locationName  varchar(70);
  declare t_targetHrs,t_workingDays double;
   declare  t_egiworkingHours,t_manaworkingHours float;
  declare t_FLAG char(1); 
  DECLARE cur1 CURSOR FOR SELECT id, name, userId, targetHrs, locationName, resourceCount, `month`, locationId, `year`, workingDays, egiworkingHours, manaworkingHours
FROM pts_manager_util  ; 
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  OPEN cur1; 

  read_loop: LOOP
    FETCH cur1 INTO t_id, t_name, t_userId, t_targetHrs, t_locationName, t_resourceCount, t_month, t_locationId, t_year, t_workingDays, t_egiworkingHours, t_manaworkingHours; 
    IF done THEN
      LEAVE read_loop;
    END IF;
     INSERT INTO pts_manager_util_bkp
(name, userId, targetHrs, locationName, resourceCount, `month`, locationId, `year`, workingDays, egiworkingHours, manaworkingHours)
VALUES(t_name, t_userId, t_targetHrs, t_locationName, t_resourceCount, t_month, t_locationId, t_year+1, t_workingDays, t_egiworkingHours, t_manaworkingHours);
   END LOOP;

  CLOSE cur1; 
END;

CREATE DEFINER=`ptsusr`@`%` PROCEDURE `pts`.`TEST_ACCOUNT`(
	IN `userId` INT
)
begin
 
declare	 counts INT;
 
 
	select  count(id)  INTO counts FROM PTS_USER_ACCOUNTS where user_id=userId;

	IF counts <=0 then
	INSERT INTO PTS_USER_ACCOUNTS
 (ACCOUNT_ID, USER_ID)
	VALUES(  1, userId);

	END IF;

end;

CREATE DEFINER=`ptsusr`@`%` PROCEDURE `pts`.`user_heirarchy_proc`(
	IN `seed` INT


)
BEGIN
DROP TABLE IF EXISTS _result;
  DROP TABLE IF EXISTS temp_user_summary;
  CREATE TEMPORARY TABLE _result (node INT PRIMARY KEY);

  
  INSERT INTO _result VALUES (seed);

  
  DROP TABLE IF EXISTS _tmp;
  CREATE TEMPORARY TABLE _tmp LIKE _result;
  REPEAT
    TRUNCATE TABLE _tmp;
    INSERT INTO _tmp SELECT user_id AS node
      FROM _result JOIN PTS.PTS_USER_SUPERVISOR ON node = supervisor_id;

    INSERT IGNORE INTO _result SELECT node FROM _tmp;
  UNTIL ROW_COUNT() = 0
  END REPEAT;
  DROP TABLE _tmp;
 
 IF seed <=-1 THEN
 create temporary table temp_user_summary select  u.ID as id ,u.name as name, (select name from PTS.PTS_USER where id=us.SUPERVISOR_ID) as supervisor,
us.SUPERVISOR_ID as supervisorId, 
						s.stream_name as streamName, r.role_name as role, ut.user_type as userType, u.username as userName, 
						u.back_fill_of as backFillOff, u.status as status, u.location as location, 
						u.location_id as location_id, l.name as location_name,l.REGION  region,
						 u.DATE_OF_BILLING as dateOfBilling, u.DOJ as doj, t.technology_name as primarySkill from PTS.PTS_USER u 
             LEFT OUTER JOIN PTS.PTS_STREAMS s on u.STREAM=s.id
              INNER JOIN PTS.PTS_USER_SUPERVISOR us on us.user_id=u.id
               INNER JOIN PTS.PTS_USER_ROLE ur on ur.USER_ID=u.id 
               INNER JOIN PTS.PTS_USER_TYPES ut on ur.TYPE_ID=ut.id 
               INNER JOIN PTS.PTS_ROLES r on ur.ROLE_ID=r.id 
               INNER JOIN PTS.PTS_LOCATION l on l.id=u.location_id
               INNER JOIN PTS.PTS_USER_SKILLS utech on utech.user_id=u.id and utech.PRIMARY_FLAG='Y'
               INNER JOIN PTS.PTS_TECHNOLOGIES t on utech.TECHNOLOGY_ID=t.id   ;           
ELSE
create temporary table temp_user_summary select  u.ID as id ,u.name as name, (select name from PTS.PTS_USER where id=us.SUPERVISOR_ID) as supervisor,
us.SUPERVISOR_ID as supervisorId, 
						s.stream_name as streamName, r.role_name as role, ut.user_type as userType, u.username as userName, 
						u.back_fill_of as backFillOff, u.status as status, u.location as location, 
						u.location_id as location_id, l.name as location_name,l.REGION  region,
						 u.DATE_OF_BILLING as dateOfBilling, u.DOJ as doj, t.technology_name as primarySkill from PTS.PTS_USER u 
             LEFT OUTER JOIN PTS.PTS_STREAMS s on u.STREAM=s.id
              INNER JOIN PTS.PTS_USER_SUPERVISOR us on us.user_id=u.id
               INNER JOIN PTS.PTS_USER_ROLE ur on ur.USER_ID=u.id 
               INNER JOIN PTS.PTS_USER_TYPES ut on ur.TYPE_ID=ut.id 
               INNER JOIN PTS.PTS_ROLES r on ur.ROLE_ID=r.id 
               INNER JOIN PTS.PTS_LOCATION l on l.id=u.location_id
               INNER JOIN PTS.PTS_USER_SKILLS utech on utech.user_id=u.id and utech.PRIMARY_FLAG='Y'
               INNER JOIN PTS.PTS_TECHNOLOGIES t on utech.TECHNOLOGY_ID=t.id   WHERE u.ID IN(SELECT node FROM PTS._result)  ;
END IF;

END;

CREATE DEFINER=`ptsusr`@`%` PROCEDURE `pts`.`user_heirarchy_proc_new`(
	IN `seed` INT


)
BEGIN
DROP TABLE IF EXISTS _result;
  DROP TABLE IF EXISTS temp_user_summary;
  CREATE TEMPORARY TABLE _result (node INT PRIMARY KEY);

  
  INSERT INTO _result VALUES (seed);

  
  DROP TABLE IF EXISTS _tmp;
  CREATE TEMPORARY TABLE _tmp LIKE _result;
  REPEAT
    TRUNCATE TABLE _tmp;
    INSERT INTO _tmp SELECT user_id AS node
      FROM _result JOIN PTS.PTS_USER_SUPERVISOR ON node = supervisor_id;

    INSERT IGNORE INTO _result SELECT node FROM _tmp;
  UNTIL ROW_COUNT() = 0
  END REPEAT;
  DROP TABLE _tmp;
 
 
  IF seed <=-1 THEN
   create temporary table temp_user_summary select  u.ID as id ,u.name as name, (select name from PTS.PTS_USER where id=us.SUPERVISOR_ID) as supervisor,
us.SUPERVISOR_ID as supervisorId, 
						s.stream_name as streamName, r.role_name as role, ut.user_type as userType, u.username as userName, 
						u.back_fill_of as backFillOff, u.status as status, u.location as location, 
						u.location_id as location_id, l.name as location_name,l.REGION  region,
						 u.DATE_OF_BILLING as dateOfBilling, u.DOJ as doj, t.technology_name as primarySkill,st.stable_team_id as stableTeam, (select teamName from PTS_STABLE_TEAMS where id=st.stable_team_id) as stableTeamName , st.contribution   from PTS.PTS_USER u 
             LEFT OUTER JOIN PTS.PTS_STREAMS s on u.STREAM=s.id
              INNER JOIN PTS.PTS_USER_SUPERVISOR us on us.user_id=u.id
               INNER JOIN PTS.PTS_USER_ROLE ur on ur.USER_ID=u.id 
               INNER JOIN PTS.PTS_USER_TYPES ut on ur.TYPE_ID=ut.id 
               INNER JOIN PTS.PTS_ROLES r on ur.ROLE_ID=r.id 
               INNER JOIN PTS.PTS_LOCATION l on l.id=u.location_id
               INNER JOIN PTS.PTS_USER_SKILLS utech on utech.user_id=u.id and utech.PRIMARY_FLAG='Y'
               INNER JOIN PTS.PTS_TECHNOLOGIES t on utech.TECHNOLOGY_ID=t.id
               INNER JOIN PTS_USER_STABLE_TEAMS st on  st.user_id = u.id and st.contribution  >0 and st.user_id = utech.user_id and  st.user_id = ur.USER_ID and   st.user_id = us.USER_ID 
                ;
 ELSE
  create temporary table temp_user_summary select  u.ID as id ,u.name as name, (select name from PTS.PTS_USER where id=us.SUPERVISOR_ID) as supervisor,
us.SUPERVISOR_ID as supervisorId, 
						s.stream_name as streamName, r.role_name as role, ut.user_type as userType, u.username as userName, 
						u.back_fill_of as backFillOff, u.status as status, u.location as location, 
						u.location_id as location_id, l.name as location_name,l.REGION  region,
						 u.DATE_OF_BILLING as dateOfBilling, u.DOJ as doj, t.technology_name as primarySkill,st.stable_team_id as stableTeam, (select teamName from PTS_STABLE_TEAMS where id=st.stable_team_id) as stableTeamName , st.contribution   from PTS.PTS_USER u 
             LEFT OUTER JOIN PTS.PTS_STREAMS s on u.STREAM=s.id
              INNER JOIN PTS.PTS_USER_SUPERVISOR us on us.user_id=u.id
               INNER JOIN PTS.PTS_USER_ROLE ur on ur.USER_ID=u.id 
               INNER JOIN PTS.PTS_USER_TYPES ut on ur.TYPE_ID=ut.id 
               INNER JOIN PTS.PTS_ROLES r on ur.ROLE_ID=r.id 
               INNER JOIN PTS.PTS_LOCATION l on l.id=u.location_id
               INNER JOIN PTS.PTS_USER_SKILLS utech on utech.user_id=u.id and utech.PRIMARY_FLAG='Y'
               INNER JOIN PTS.PTS_TECHNOLOGIES t on utech.TECHNOLOGY_ID=t.id
               INNER JOIN PTS_USER_STABLE_TEAMS st on  st.user_id = u.id and st.contribution  >0 and st.user_id = utech.user_id and  st.user_id = ur.USER_ID and   st.user_id = us.USER_ID 
             WHERE u.ID IN(SELECT node FROM PTS._result)  ;
            END IF;
END;

CREATE DEFINER=`ptsusr`@`%` PROCEDURE `pts`.`user_id_heirarchy_proc`(
	IN `seed` INT

)
BEGIN
  
  DROP TABLE IF EXISTS  _result;
  CREATE TEMPORARY TABLE _result (node INT PRIMARY KEY);

  
  INSERT INTO _result VALUES (seed);

  
  DROP TABLE IF EXISTS _tmp;
  CREATE TEMPORARY TABLE _tmp LIKE _result;
  REPEAT
    TRUNCATE TABLE _tmp;
    INSERT INTO _tmp SELECT user_id AS node
      FROM _result JOIN PTS.PTS_USER_SUPERVISOR ON node = supervisor_id;

    INSERT IGNORE INTO _result SELECT node FROM _tmp;
  UNTIL ROW_COUNT() = 0
  END REPEAT;
  DROP TABLE _tmp;
  
END;





CREATE DEFINER=`ptsusr`@`%` TRIGGER `PTS_USER_before_insert` AFTER INSERT ON `PTS_USER` FOR EACH ROW BEGIN
DECLARE finished INTEGER DEFAULT 0;
DECLARE n INT DEFAULT 0;
DEClARE networkCur CURSOR FOR SELECT id FROM PTS_NETWORK_CODES n WHERE  n.PROJECT_LEVEL='ACCOUNT'  and STATUS='Execution';
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
        
 OPEN networkCur;
 getEmail: LOOP
 	FETCH networkCur INTO n;
  IF finished = 1 THEN 
            LEAVE getEmail;
  END IF;
INSERT INTO PTS_USER_NETWORK_CODES (user_id ,NETWORK_CODE_ID) VALUES(NEW.id,n);
 END LOOP getEmail;
 CLOSE networkCur;

END;

CREATE DEFINER=`ptsusr`@`%` TRIGGER `TIMESHEET_UPD_TRIGGER` BEFORE UPDATE ON `PTS_USER_TIMESHEET` FOR EACH ROW BEGIN

insert into PTS.PTS_USER_TIMESHEET_LOG (TIMESHEET_ID, ACTION_TYPE, USER_ID, 
OLD_NETWORK_CODE_ID, NEW_NETWORK_CODE_ID, 
OLD_ACTIVITY_CODE_ID, NEW_ACTIVITY_CODE_ID, 
OLD_MON_HRS, NEW_MON_HRS, 
OLD_TUE_HRS, NEW_TUE_HRS, 
OLD_WED_HRS, NEW_WED_HRS, 
OLD_THU_HRS, NEW_THU_HRS, 
OLD_FRI_HRS, NEW_FRI_HRS, 
OLD_SAT_HRS, NEW_SAT_HRS, 
OLD_SUN_HRS, NEW_SUN_HRS, 
WEEKENDING_DATE, CREATED_BY, CREATED_DATE, 
OLD_TYPE, NEW_TYPE, 
STATUS, UPDATED_BY, UPDATED_DATE) values
(OLD.ID, 'MODIFY', NEW.USER_ID, 
OLD.NETWORK_CODE_ID, NEW.NETWORK_CODE_ID, 
OLD.ACTIVITY_CODE_ID, NEW.ACTIVITY_CODE_ID, 
OLD.MON_HRS, NEW.MON_HRS, 
OLD.TUE_HRS, NEW.TUE_HRS, 
OLD.WED_HRS, NEW.WED_HRS, 
OLD.THU_HRS, NEW.THU_HRS, 
OLD.FRI_HRS, NEW.FRI_HRS, 
OLD.SAT_HRS, NEW.SAT_HRS, 
OLD.SUN_HRS, NEW.SUN_HRS, 
OLD.WEEKENDING_DATE, OLD.CREATED_BY, OLD.CREATED_DATE, 
OLD.TYPE, NEW.TYPE, 
NEW.STATUS, NEW.UPDATED_BY, NEW.UPDATED_DATE);

END;

CREATE DEFINER=`ptsusr`@`%` TRIGGER `TIMESHEET_DEL_TRIGGER` BEFORE DELETE ON `PTS_USER_TIMESHEET` FOR EACH ROW BEGIN

insert into PTS.PTS_USER_TIMESHEET_LOG (
TIMESHEET_ID, ACTION_TYPE, USER_ID, OLD_NETWORK_CODE_ID, OLD_ACTIVITY_CODE_ID, OLD_MON_HRS, OLD_TUE_HRS, OLD_WED_HRS, OLD_THU_HRS, OLD_FRI_HRS, OLD_SAT_HRS, OLD_SUN_HRS, 
WEEKENDING_DATE, CREATED_BY, CREATED_DATE, OLD_TYPE, STATUS, UPDATED_BY, UPDATED_DATE) values
(OLD.ID, 'DELETE', OLD.USER_ID, OLD.NETWORK_CODE_ID, OLD.ACTIVITY_CODE_ID, OLD.MON_HRS, OLD.TUE_HRS, OLD.WED_HRS, OLD.THU_HRS, OLD.FRI_HRS, OLD.SAT_HRS, OLD.SUN_HRS, OLD.WEEKENDING_DATE, OLD.CREATED_BY, OLD.CREATED_DATE, OLD.type, OLD.STATUS, OLD.UPDATED_BY, OLD.UPDATED_DATE);

END;
