<?php
$apikey = 4711;
if (isset($_GET["health"]) && $_GET["health"] == "get") {
    echo 'System works fine!';
} else if (isset($_GET["apikey"]) && $_GET["apikey"] == "get") {
    echo "Your key is " . $apikey;
} else if (isset($_GET["data"]) && $_GET["data"] == "true") {
    $sql = new TSH_SQL();
    $sql->setUpload();
} else {
    echo "Nothing to show";
}

/**
 * https://www.coderblog.de/sending-data-from-java-to-php-via-a-post-request/
 * foreach ($_POST as $key => $value) {
 * switch ($key) {
 * case 'firstKey':
 * $firstKey = $value;
 * break;
 * case 'secondKey':
 * $secondKey = $value;
 * break;
 * default:
 * break;
 * }
 * }
 */
class TSH_SQL extends DateTime
{
    
    public function openDBConnection()
    {
        /**
         * ****************************************************************************************************************************************
         * Open SQL-Connection
         * ****************************************************************************************************************************************
         */
        $host = "rdbms.strato.de";
        $table = "DB3162645";
        $username = "U3162645";
        $pw = "H4Cd84V!5";
        $this->mysqli = new mysqli($host, $username, $pw, $table);
        
        if ($this->mysqli->connect_error) {
            echo "Error: " . mysqli_connect_error();
            exit();
        } else {
            // echo "successfully! \n";
            // this.closeDBConnection();
            return true;
            // echo ("<br>Connected successfully"); //Control-Msg
        }
    }
    
    private function closeDBConnection()
    {
        /**
         * ****************************************************************************************************************************************
         * Close SQL-Connection
         * ****************************************************************************************************************************************
         */
        $this->mysqli->close();
        // echo ("<br>Disconnected successfully"); //Control-Msg
    }
    
    private function sql($query)
    {
        /**
         * ****************************************************************************************************************************************
         * Generic SQL-Query-Maker
         * ****************************************************************************************************************************************
         */
        echo "\nstarting query";
        $this->openDBConnection();
        // printf ( "Initial character set: %s\n", $this->mysqli->character_set_name () ); //Control-Msg
        
        if (! $this->mysqli->set_charset('utf8')) {
            printf("Error loading character set utf8: %s\n", $this->mysqli->error);
            exit();
        }
        //
        echo "New character set information:\n";
        // print_r ( $this->mysqli->get_charset () ); //Control-Msg
        for($i=0;
        $i < 100;$i++){
            $this->mysqli->query($query);
        }
        if (! $this->mysqli->query($query)) {
            printf("Errormessage: %s\n", $this->mysqli->error);
        } else {
            echo "\nquery done";
            // echo "<br><br>";
            // echo $mtb; //Control-Msg
            // echo "<br><br>";
        }
        if (isset($result)) {
            $result->free();
        }
        $this->closeDBConnection();
        return true;
    }
    
    public function setUpload()
    {
        $xPos = 0;
        $yPos = 128;
        $zPos = 65535;
        $dis = 20;
        
        $t = date('Y-m-d H:i:s');
        $deviceID = "Rpi1";
        $appID = "tshV0.1";
        // $query = 'START TRANSACTION;';
        $query = 'INSERT INTO `HUC_VIS`(`xPos`, `yPos`, `zPos`, `distance`, `t`, `deviceID`, `appID`) VALUES (' . $xPos . ',' . $yPos . ',' . $zPos . ',' . $dis . ',"' . $t . '","' . $deviceID . '","' . $appID . '");';
        // $query .= 'COMMIT;';
        
        echo $query;
        echo "no error";
        $this->sql($query);
        return false;
    }
    
    public function readUpload()
    {
        $query = "SELECT * FROM `HUC_VIS`";
        $this->sql($query);
    }
}

?>