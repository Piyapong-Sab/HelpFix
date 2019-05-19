<?php 

include('connect.php');

$mysql_query= "SELECT 	iss.Id_issue , iss.Approve_date , CONCAT(US.Name, ' ', US.Surname) AS Sent_approve_by , iss.Price , iss.Approve_status ,
CONCAT(VUS.Name, ' ', VUS.Surname) AS Create_by , iss.Problem , iss.Place , CONCAT(VUSE.Name, ' ', VUSE.Surname) AS Assessment_by,
iss.Euipment_used 
FROM issue iss INNER JOIN user US 
ON iss.Sent_approve_by = US.Id_user INNER JOIN user VUS
ON iss.Create_by = VUS.Id_user INNER JOIN user VUSE
ON iss.Assessment_by = VUSE.Id_user
WHERE Approve_status = 'อนุมัติ';";

$response = mysqli_query($conn,$mysql_query);

if(mysqli_num_rows($response)>0){
 // output data of each row
 while($row[] = mysqli_fetch_assoc($response)) { 
 $tem = $row;
 $json = json_encode($tem);
 }
} else {
 echo "0 results";
}
 echo $json;
mysqli_close($conn); 
?> 