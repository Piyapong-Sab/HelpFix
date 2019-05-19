<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
include('connect.php');

$IDUser = $_POST['IDUser'];
$IDIssue = $_POST['IDIssue'];
$assign_by = $_POST['assign_by'];
$priority = $_POST['priority'];
$cutAssign_by = substr($assign_by ,0,13);


$mysql_query= "UPDATE `issue` SET  `Assessment_date` = now() , `Priority` = '$priority' , 
`Assessment_by` = '$cutAssign_by' , `Assigner_by` = '$IDUser' , `Id_status` = '103'
Where `Id_issue` = '$IDIssue';";


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