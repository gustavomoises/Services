<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String CustId="";
	//if it is a new session and that the user is not logged in, redirect to the login page
	if ((session.isNew()) 
			|| (session.getAttribute("logged_in") == null) 
			|| (!session.getAttribute("logged_in").equals(true)))
	{
		response.sendRedirect("login.jsp");
	}
	else
	{
		//Retrieve Customer Id from session
		CustId= (String)session.getAttribute("customerId");
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reward Page</title>
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script>
	//set up function to get all rewards and display in select options
	function loadRewards()
	{
		var rewardselect=$("#rewardlist")[0];
		var url ="http://localhost:8081/JSPDay3RESTExample/rs/reward/getrewards";  
		$.get(url, function(json){
			
			for(i=0;i<json.length;i++)
				{
					var option = document.createElement("option");
					option.text = json[i].RwdName;
					option.value= json[i].RewardId;
					rewardselect.add(option);
				}
		},"json");
	}

	//set up function to load the selected rewar dnumber and display
	function loadForm(rewardId) {
		console.log("in loadForm, rewardId=" + rewardId);
		var req = new XMLHttpRequest();

		req.onreadystatechange = function() {
			if (req.readyState == 4 && req.status == 200) {
				var rwdcustomer = JSON.parse(req.responseText);
				//Hidden field for rewardId
				document.getElementById("rewardId").value = rwdcustomer.RewardId+"";
				//Reward number of a specific customerId and rewardId
				document.getElementById("rwdNumber").value = rwdcustomer.RwdNumber;
				//Hidden field for customer Id
				document.getElementById("customerId").value = rwdcustomer.CustomerId+"";
				//Hidden field to identify if should do insert or update customer reward number
				document.getElementById("post").value = rwdcustomer.post;
			}
		}
		//CustId from session
		req.open("GET", "http://localhost:8081/JSPDay3RESTExample/rs/reward/getrewardnumberbyRwdIdCustId/" + rewardId+ "/"+<%=CustId%>); 
		req.send();

	}

	//set up function to update customer Reward
	function updateCustomerReward() {
		console.log("in updateRewardCustomer");
		
		//get a collection of the child nodes inside the div of fields in the agentupdate.html file 
		var divChildren = $("#myFields input");
		//create a JSON object shell
		var mydata = {};
		//loop through the fields and add the fieldname and value to the object
		for (i = 0; i < divChildren.length; i++) {
			mydata[divChildren[i].id] = divChildren[i].value;

		}
		if (divChildren[0].value=="")
		{
			divChildren[3].value=0;
			console.log("Empty string delete customer reward")
		}
		console.log(JSON.stringify(mydata));

		$.ajax({
			url:"http://localhost:8081/JSPDay3RESTExample/rs/reward/postcustomerreward",
			type:"POST",
			data:JSON.stringify(mydata),
			complete:function(req,stat){ $("#message").html(stat); },
			success:function(data){ $("#message").append("<br />" + data); },
			dataType:"text",
			contentType:"application/json; charset=UTF-8"
		});
		//loadForm(divChildren[2].value);
	}
	
	//set up function to update insert customer Reward
	function insertCustomerReward() {
		console.log("in insertRewardCustomer");
		
		//get a collection of the child nodes inside the div of fields in the agentupdate.html file 
		var divChildren = $("#myFields input");
		//create a JSON object shell
		var mydata = {};
		//loop through the fields and add the fieldname and value to the object
		for (i = 0; i < divChildren.length; i++) {
			mydata[divChildren[i].id] = divChildren[i].value;

		}
		divChildren[3].value=1;

		
		console.log(JSON.stringify(mydata));

		$.ajax({
			url:"http://localhost:8081/JSPDay3RESTExample/rs/reward/putcustomerreward",
			type:"PUT",
			data:JSON.stringify(mydata),
			complete:function(req,stat){ $("#message").html(stat); },
			success:function(data){ $("#message").append("<br />" + data); },
			dataType:"text",
			contentType:"application/json; charset=UTF-8"
		});

	}
	
	// set up function to update customer Reward
	function sendCustomerReward(){
		console.log("in sendRewardCustomer");
		//get a collection of the child nodes inside the div of fields in the agentupdate.html file 
		var divChildren = $("#myFields input");
		if (divChildren[3].value==0)
		{

			insertCustomerReward();
		}
		else
		{

			updateCustomerReward();
			
		}
	}
	
	function Logout(){
		console.log("in Logout");
		<%
		session.setAttribute("logged_in", false);
		//response.sendRedirect("login.jsp");
		%>
		
	}
	
	
	
	
</script>
</head>
<body>
	<h1>Customer Reward Page <button onclick="Logout()">Logout</button></h1> 
	<p> Reward:
	<select id="rewardlist" onchange=" loadForm(this.value)">
		<option value="">Select a Reward </option>
	</select>

	 
	<div id="myFields">
		Reward Number:<input type="text" id="rwdNumber" /><br />
		<input type="hidden" id="customerId" /><br />
		<input type="hidden" id="rewardId" /><br />
		<input type="hidden" id="post" />
	</div>
	<button onclick="sendCustomerReward()">Update</button>
	
	<div id="message"></div>

	<script>
		$(document).ready(function(){loadRewards(); })
	
	</script>
</body>
</html>