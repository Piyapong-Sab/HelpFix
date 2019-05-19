<?php 

include('connect.php');

$LogID = $_POST['LogID'];

$mysql_query= "SELECT 	Id_issue , Evaluate_date , Priority , Price , st.Status_name , Problem , Place , Level
FROM issue iss  INNER JOIN status st 
ON st.Id_status = iss.Id_status
where Price IS NOT NULL AND Price != '0' AND st.Status_name = 'รอซ่อม'or st.Status_name = 'รออนุมัติ'
AND Assessment_by = '$LogID';";

$response = mysqli_query($conn,$mysql_query);

$result = array();
$result['data']= array();


if(mysqli_num_rows($response)>0){
    while($row  = mysqli_fetch_assoc($response)){
    
        $index['Id_issue'] = $row["Id_issue"];
        $index['Evaluate_date'] = $row["Evaluate_date"];
        $index['Priority'] = $row["Priority"];
        $index['Price'] = $row["Price"];
        $index['Status_name'] = $row["Status_name"];
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
