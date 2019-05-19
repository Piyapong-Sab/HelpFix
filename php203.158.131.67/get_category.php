<?php

include('connect.php');
$mysql_query = "SELECT * FROM main_category ;";
$response = mysqli_query($conn,$mysql_query);

$result = array();
$result['data']= array();

if(mysqli_num_rows($response)>0){
    while($row  = mysqli_fetch_assoc($response)){
    
        $index['Id_m_category'] = $row["Id_m_category"];
        $index['Mcategory_name'] = $row["Mcategory_name"];

        array_push($result['data'],$index);
        $result['status']="true";
        $result['message'] ="Data fetched successfully!";
        //echo "true,".$index['Id_m_category'].",".$index['Mcategory_name'].",".$index['roleid'];
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