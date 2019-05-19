<?php 

if($_SERVER['REQUEST_METHOD'] =='POST'){
    include('connect.php');
  
    $Cre_id = $_POST['Cre_id'];
    $User_by = $_POST['User_by'];
    $Pla_ce = $_POST['Pla_ce'];
    $Lev_el = $_POST['Lev_el'];
    $Pro_blem = $_POST['Pro_blem'];
    $Pic_is = $_POST['Pic_is'];
    $Category = $_POST['Category'];
    $SCategory = $_POST['SCategory'];
    $cutCategory = substr($Category ,0,3);
    $cutSCategory = substr($SCategory ,0,3);

    //$finalPath = "C://http://203.158.131.67/~devhelpfix/apphelpfix/pic_create/$Cre_id.jpg";
    

    $sql = " INSERT INTO `issue`(`Id_issue` , `Id_m_category` , `Id_s_category`, `Id_status`, 
    `Record_date`,`Create_by` , `Place`,`Level`, `Problem`, `Pic_issue`)
    VALUES ('$Cre_id' ,'$cutCategory' , '$cutSCategory' , '101' , now() , '$User_by' , '$Pla_ce' ,'$Lev_el' , '$Pro_blem' , '$Pic_is');";

    if (mysqli_query($conn ,$sql )) {

        file_put_contents( $finalPath , base64_decode($Pic_is));
        echo "Success";
        mysqli_close($conn);
    }
}

?>