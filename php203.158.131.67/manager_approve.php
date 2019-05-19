<?php

	
if($_SERVER['REQUEST_METHOD'] =='POST'){

    include ('connect.php');

    $id_issue = $_POST['id_issue'];
    $Approve_by = $_POST['Approve_by'];
   


   $sql = "UPDATE issue SET Approve_by ='$Approve_by' ,id_status = '106' , Approve_status = 'อนุมัติ' WHERE Id_issue = '$id_issue'";

  if (mysqli_query($conn ,$sql )) {
  	echo "Success";
  }
 }

?>