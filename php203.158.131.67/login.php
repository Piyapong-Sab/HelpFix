<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

include('connect.php');

$id_user = $_POST['id_user'];
$password = $_POST['password'];
//test the login
//$user_email = "ron@tabian.ca";
//$user_pass = "ronspassword";
$mysql_query = "SELECT `id_user` as userid,`Id_role` as roleid ,`Password`,CONCAT(Name, ' ', Surname) AS name , `email`
 FROM user WHERE id_user = '$id_user'
AND Password = '$password'";
$response = mysqli_query($conn,$mysql_query);

$result = array();
$result['login']= array();


//check the result
if(mysqli_num_rows($response)>0){
    $row = mysqli_fetch_assoc($response);
    $index['userid'] = $row["userid"];
	$index['name'] = $row["name"];
    $index['email'] = $row["email"];
    $index['roleid'] = $row["roleid"];
    
    array_push($result['login'],$index);
    $result['success']="1";
    $result['message'] ="success";
    echo json_encode($result);
    echo "true,".$index['name'].",".$index['email'].",".$index['roleid'];
	//Here is the specially formatted string response.. ie: "ServerResponse".
	//It is of the form: "boolean,email,name"
    //echo "true,".$email.",".$name;
    
}
else{
    $result['success']="0";
    $result['message'] ="error";
    echo json_encode($result);
	echo "Login was not successful... :(";
}
 
?>