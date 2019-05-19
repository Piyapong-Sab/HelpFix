<?php

include('connect.php');

$mysql_query= "SELECT 	iss.Id_issue , iss.Record_date ,CONCAT(u.Name, ' ', u.Surname) AS Name, mc.Mcategory_name , sc.Scategory_name, iss.Problem , iss.Pic_issue , st.Status_name ,iss.Place , iss.Level
FROM issue iss INNER JOIN status st 
ON st.Id_status = iss.Id_status INNER JOIN user u 
ON u.Id_user = iss.Create_by INNER JOIN main_category mc
ON mc.Id_m_category = iss.Id_m_category INNER JOIN sub_category sc 
on sc.Id_s_category = iss.Id_s_category
WHERE st.Status_name = 'ส่งใบคำร้อง';";

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