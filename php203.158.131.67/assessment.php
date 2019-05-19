<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
include('connect.php');

$IDUser = $_POST['IDUser'];
$EUIpment_UD = $_POST['EUIpment_UD'];
$IDIssue = $_POST['IDIssue'];
$price = $_POST['price'];

$mysql_query= "UPDATE issue SET Evaluate_date = now() , Euipment_used = '$EUIpment_UD' , Price = '$price'
Where Id_issue = '$IDIssue';";


if(mysqli_query($conn,$mysql_query)){
    $result['message'] ="success";
    echo json_encode($result);
}
else{
    $result['message'] ="error";
    echo json_encode($result);
	echo "Login was not successful... :(";
}
}

?>