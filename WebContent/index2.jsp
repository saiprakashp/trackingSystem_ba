<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Add React in One Minute</title>
</head>
<body>

	<h2>Add React in One Minute</h2>
	<p>This page demonstrates using React with no build tooling.</p>
	<p>React is loaded as a script tag.</p>

	<p>
		This is the first comment.
		<!-- We will put our React component inside this div. -->
	<div class="like_button_container" data-commentid="1"></div>
	</p>

	<p>
		This is the second comment.
		<!-- We will put our React component inside this div. -->
	<div class="like_button_container" data-commentid="2"></div>
	</p>

	<p>
		This is the third comment.
		<!-- We will put our React component inside this div. -->
	<div class="like_button_container" data-commentid="3"></div>
	</p>

	<!-- Load React. -->
	<!-- Note: when deploying, replace "development.js" with "production.min.js". -->
	<script src="<%=request.getContextPath()%>/js/react.development.js"></script>
	<script src="<%=request.getContextPath()%>/js/react-dom.development.js"></script>
	<script src="<%=request.getContextPath()%>/js/babel.min.js"></script>

	<!-- Load our React component. -->
	<script src="like_button.js"></script>

</body>
</html>