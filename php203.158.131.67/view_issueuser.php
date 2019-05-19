<?php 

    include('connect.php');

    $LogID = $_POST['LogID'];


$mysql_query= "SELECT 	iss.Id_issue , iss.Record_date , iss.Problem  , st.Status_name ,iss.Place , iss.Level
FROM issue iss INNER JOIN status st 
ON st.Id_status = iss.Id_status
WHERE Create_by = '$LogID'
ORDER BY Id_issue DESC";

$response = mysqli_query($conn,$mysql_query);

$result = array();
$result['data']= array();

if(mysqli_num_rows($response)>0){
    while($row  = mysqli_fetch_assoc($response)){
    
        $index['Id_issue'] = $row["Id_issue"];
        $index['Record_date'] = $row["Record_date"];
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

