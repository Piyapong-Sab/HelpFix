<?php

include('connect.php');

$LogID = $_POST['LogID'];
$mysql_query= "SELECT 	iss.Id_issue , iss.Assessment_date , iss.Priority , CONCAT(u.Name, ' ', u.Surname) AS Assessment_by 
, mc.Mcategory_name , sc.Scategory_name, iss.Problem , st.Status_name ,iss.Place , iss.Level
FROM issue iss INNER JOIN status st 
ON st.Id_status = iss.Id_status INNER JOIN user u 
ON u.Id_user = iss.Assessment_by INNER JOIN main_category mc
ON mc.Id_m_category = iss.Id_m_category INNER JOIN sub_category sc 
on sc.Id_s_category = iss.Id_s_category
WHERE st.Status_name = 'รอซ่อม'
AND Assigner_by = '$LogID';";

$response = mysqli_query($conn,$mysql_query);

$result = array();
$result['data']= array();


if(mysqli_num_rows($response)>0){
    while($row  = mysqli_fetch_assoc($response)){
    
        $index['Id_issue'] = $row["Id_issue"];
        $index['Assessment_date'] = $row["Assessment_date"];
        $index['Priority'] = $row["Priority"];
        $index['Assessment_by'] = $row["Assessment_by"];
        $index['Mcategory_name'] = $row["Mcategory_name"];
        $index['Scategory_name'] = $row["Scategory_name"];
        $index['Problem'] = $row["Problem"];
        $index['Status_name'] = $row["Status_name"];
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
