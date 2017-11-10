CREATE TABLE HUC_VIS (
	id int NOT NULL AUTO_INCREMENT,
	xPos int NOT NULL,
	yPos int NOT NULL,
	zPos int NOT NULL,
	distance int,
	t datetime NOT NULL,
	deviceID varchar (100) NOT NULL,
	appID varchar (100) NOT NULL,
	PRIMARY KEY (id)
)