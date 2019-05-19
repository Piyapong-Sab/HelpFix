<?php

include('connect.php');

$LogID = $_POST['LogID'];

$mysql_query= "SELECT  `Id_issue` , CONCAT(US.Name, ' ', US.Surname) AS Assigner_by , Assessment_date , Priority  
, iss.Problem , iss.Place , iss.Level
FROM issue iss INNER JOIN user US 
ON US.Id_user = iss.Assigner_by
WHERE iss.Euipment_used IS NULL and iss.Id_status = '103' AND Assessment_by = '$LogID';";

$response = mysqli_query($conn,$mysql_query);

$result = array();
$result['data']= array();


if(mysqli_num_rows($response)>0){
    while($row  = mysqli_fetch_assoc($response)){
    
        $index['Id_issue'] = $row["Id_issue"];
        $index['Assigner_by'] = $row["Assigner_by"];
        $index['Assessment_date'] = $row["Assessment_date"];
        $index['Priority'] = $row["Priority"];
        $index['Problem'] = $row["Problem"];
        $index['Place'] = $row["Place"];
        $index['Level'] = $row["Level"];

        array_push($result['data'],$index);
        $result['status']="true";
        $result['message'] ="Data fetched successfully!";
 }
  
}else{
  $result['success']="0";
  $result['message'] ="error";
  echo json_encode($result);
echo "category was not successful... :(";
}
echo json_encode($result);
mysqli_close($conn); 
?>
