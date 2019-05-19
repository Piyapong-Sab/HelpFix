<?php 

if($_SERVER['REQUEST_METHOD'] =='POST'){
    include('connect.php');
  
    $ISSUEID = $_POST['ISSUEID'];
    $PIC_SUCCESS = $_POST['PIC_SUCCESS'];

    //$finalPath = "http://203.158.131.67/~devhelpfix/apphelpfix/pic_success/$ISSUEID.jpg";


    $sql = " UPDATE `issue` SET Id_status = '104' , Pic_success = '$PIC_SUCCESS' WHERE Id_issue = '$ISSUEID' ; ";
    
    if (mysqli_query($conn ,$sql )) {

        file_put_contents( $finalPath , base64_decode($PIC_SUCCESS));
        echo "Success";
        mysqli_close($conn);
    }
}

?>