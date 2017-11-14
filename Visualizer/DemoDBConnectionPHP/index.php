<?php
/**
 * Created by PhpStorm.
 * User: Markus Danilow
 * Date: 13.11.2017
 * Time: 11:25
 */


$con = new mysqli('rdbms.strato.de', 'U3162645', 'H4Cd84V!5', 'DB3162645');

$sql = <<<SQL
    SELECT * 
SQL;

if(!$result = $db->query($sql)){
    die('There was an error running the query [' . $db->error . ']');
}

$con->close();