<?php

$ftp_server = "10.10.100.67";
$ftp_user = "devhelpfix";
$ftp_pass = "Helpfix@2562";

//$localFilePath  = 'localfile.txt';
//$remoteFilePath = 'public_html/localfile.txt';
//$destination_file  =  "/home/devhelpfix/public_html/apphelpfix/pic_create/localfile.txt " ;

// set up a connection or die
$conn_id = ftp_connect($ftp_server) or die("Couldn't connect to $ftp_server"); 

// try to logina
if (@ftp_login($conn_id, $ftp_user, $ftp_pass)) {
    echo "Connected as $ftp_user@$ftp_server\n";
    
    
    $file = "/home/devhelpfix/localfile.txt";

    // upload file
    if (ftp_put($conn_id, "serverfile.txt", $file, FTP_ASCII))
      {
      echo "Successfully uploaded $file.";
      }
    else
      {
      echo "Error uploading $file.";
      }
    
 
} else {
    echo "Couldn't connect as $ftp_user\n";
}



// close the connection
ftp_close($conn_id);  
?>