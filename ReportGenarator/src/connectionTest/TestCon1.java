/*    */ package connectionTest;
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ 
/*    */ public class TestCon1 {
/*    */   public static void main(String[] args) {
/*    */     try {
/* 10 */       Class.forName("com.mysql.jdbc.Driver");
/* 11 */       System.out.println("--------------------------");
/* 12 */       DriverManager.setLoginTimeout(10);
/* 13 */       Connection connection = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/PTS", "ptsusr", 
/* 14 */           "ptspass");
/* 15 */       System.out.println("CONNECTION: " + connection);
/* 16 */       PreparedStatement ps = connection
/* 17 */         .prepareStatement("SELECT * FROM PTS_USER_SUPERVISOR WHERE user_id=383 ");
/* 18 */       ResultSet rs = ps.executeQuery();
/* 19 */       rs.next();
/* 20 */       System.out.println(rs.getLong("ID"));
/* 21 */       rs.close();
/* 22 */       ps.close();
/* 23 */       connection.close();
/*    */     }
/* 25 */     catch (Throwable th) {
/* 26 */       th.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\SAI\Workspace\rico_pts\rico_pts_current\reportGenerator\Reports.jar!\connectionTest\TestCon1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */