<?php

	
if($_SERVER['REQUEST_METHOD'] =='POST'){

    include ('connect.php');

    $IDUSER = $_POST['IDUSER'];
    $IDISSUE = $_POST['IDISSUE'];


   $sql = "UPDATE issue SET Approve_date = now() , Sent_approve_by ='$IDUSER' ,id_status = '105' ,`Approve_status` = 'ยกเลิกซ่อม' WHERE Id_issue = '$IDISSUE' ;";

  if (mysqli_query($conn ,$sql )) {
  	echo "Success";
  }
 }

?>