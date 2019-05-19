<?php 

include('connect.php');

$LogID = $_POST['LogID'];
$mysql_query= "SELECT 	Id_issue , Evaluate_date , Priority , Problem , st.Status_name , Place ,Price, Level
FROM issue iss  INNER JOIN status st 
ON st.Id_status = iss.Id_status
where   st.Status_name = 'อนุมัติ' AND `Evaluate_date` IS NOT  NULL OR iss.Price = '0' and  st.Status_name != 'ซ่อมเสร็จ'
AND Assessment_by = '$LogID' ;";


$response = mysqli_query($conn,$mysql_query);

$result = array();
$result['data']= array();


if(mysqli_num_rows($response)>0){
    while($row  = mysqli_fetch_assoc($response)){
    
        $index['Id_issue'] = $row["Id_issue"];
        $index['Evaluate_date'] = $row["Evaluate_date"];
        $index['Priority'] = $row["Priority"];
        $index['Status_name'] = $row["Status_name"];
        $index['Problem'] = $row["Problem"];
        $index['Place'] = $row["Place"];
        $index['Price'] = $row["Price"];
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
