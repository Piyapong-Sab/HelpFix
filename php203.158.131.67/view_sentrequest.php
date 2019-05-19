<?php 

include('connect.php');

$LogID = $_POST['LogID'];
$mysql_query= "SELECT 	iss.Id_issue , iss.Approve_date , CONCAT(US.Name, ' ', US.Surname) AS Assessment_by  ,
CONCAT(u.Name, ' ', u.Surname) AS Create_by , iss.Approve_status ,iss.Problem , iss.Place , iss.Level , iss.Euipment_used , iss.Price
FROM issue iss INNER JOIN user US 
ON iss.Assessment_by = US.Id_user INNER JOIN user u
ON u.Id_user = iss.Create_by
WHERE Approve_status = 'รออนุมัติ'
AND Sent_approve_by = '$LogID' ;";


$response = mysqli_query($conn,$mysql_query);

$result = array();
$result['data']= array();


if(mysqli_num_rows($response)>0){
    while($row  = mysqli_fetch_assoc($response)){
    
        $index['Id_issue'] = $row["Id_issue"];
        $index['Approve_date'] = $row["Approve_date"];
        $index['Assessment_by'] = $row["Assessment_by"];
        $index['Create_by'] = $row["Create_by"];
        $index['Approve_status'] = $row["Approve_status"];
        $index['Problem'] = $row["Problem"];
        $index['Place'] = $row["Place"];
        $index['Level'] = $row["Level"];
        $index['Euipment_used'] = $row["Euipment_used"];
        $index['Price'] = $row["Price"];

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
