
<?php

include('connect.php');
/*
  $sql = "SELECT MAX(Id_issue) + 1 AS lastid FROM issue ";
  $result = mysqli_query($conn , $sql);
  $stuff = mysqli_fetch_assoc($result);
  echo 'Last record is : '.$stuff[lastid];
*/
$mysql_query = "SELECT MAX(Id_issue) + 1 AS lastid FROM issue ";
  $response = mysqli_query($conn,$mysql_query);

$result = array();
$result['maxid']= array();

if(mysqli_num_rows($response)>0){
    $row = mysqli_fetch_assoc($response);
    $index['lastid'] = $row["lastid"];
    array_push($result['maxid'],$index);

    $result['success']="1";
    $result['message'] ="success";

    echo json_encode($result);
    //echo "true,".$index['lastid'];
}
else{
    $result['success']="0";
    $result['message'] ="error";
    echo json_encode($result);
	echo  "was not successful... :(";
}
?> 