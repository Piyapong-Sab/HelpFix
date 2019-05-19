<?php

include('connect.php');

$mysql_query= "SELECT 	iss.Id_issue ,  iss.Record_date ,CONCAT(us.Name, ' ', us.Surname) AS Create_by , iss.Problem , iss.Place ,iss.Level , iss.Assessment_date  ,iss.Priority , iss.Evaluate_date, CONCAT(u.Name, ' ', u.Surname) AS Assessment_by , iss.Euipment_used , iss.Price, st.Status_name 
FROM issue iss INNER JOIN status st 
ON st.Id_status = iss.Id_status INNER JOIN user u 
ON u.Id_user = iss.Assessment_by INNER JOIN user us  
ON us.Id_user = iss.Create_by
WHERE iss.Price != '' and st.Status_name = 'รอซ่อม';";

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