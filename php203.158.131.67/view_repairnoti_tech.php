<?php 

include('connect.php');

$LogID = $_POST['LogID'];
$mysql_query= "SELECT 	Id_issue ,Assessment_date ,  Problem , st.Status_name  , CONCAT(us.Name, ' ', us.Surname) AS Assigner_by 
, Place , Level , Priority
FROM issue iss  INNER JOIN status st 
ON st.Id_status = iss.Id_status INNER JOIN user us 
ON us.Id_user = iss.Assigner_by
where  st.Status_name = 'ซ่อมเสร็จ'
AND Assessment_by = '$LogID' 
ORDER BY Id_issue DESC;";

$response = mysqli_query($conn,$mysql_query);

$result = array();
$result['data']= array();


if(mysqli_num_rows($response)>0){
    while($row  = mysqli_fetch_assoc($response)){
    
        $index['Id_issue'] = $row["Id_issue"];
        $index['Assessment_date'] = $row["Assessment_date"];
        $index['Assigner_by'] = $row["Assigner_by"];
        $index['Status_name'] = $row["Status_name"];
        $index['Problem'] = $row["Problem"];
        $index['Place'] = $row["Place"];
        $index['Level'] = $row["Level"];
        $index['Priority'] = $row["Priority"];

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
