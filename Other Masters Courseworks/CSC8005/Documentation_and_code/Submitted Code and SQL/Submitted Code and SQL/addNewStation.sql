SET @newStation = 'Durham';
SET @connectingStation = 'Gateshead';
SET @routeFromNewStation = 'Dur-Gat';
SET @routeFromCurrentStation = 'Gat-Dur';
SET @minutesBetweenStations = 0.15;
SET @startHour = 8;
SET @endHour = 23;

#Set minutes train departs at lines 34-37 and lines 56-59


INSERT INTO `t8005t3`.`final_metro_trainstations`
	VALUES
	(@newStation);



INSERT INTO `t8005t3`.`final_metro_cost`
	VALUES
	(@routeFromNewStation, @newStation, @connectingStation,@minutesBetweenStations),
	(@routeFromCurrentStation, @connectingStation, @newStation, @minutesBetweenStations);
SET @journeyHours = @endHour-@startHour;

DELIMITER $$
CREATE PROCEDURE newStat1()
	BEGIN
		DECLARE counter INT DEFAULT 0;
		DECLARE id INT DEFAULT (SELECT MAX(depNo) FROM `t8005t3`.`final_metro_week_dep`);

		WHILE counter<(@journeyHours) DO
			INSERT INTO `t8005t3`.`final_metro_weekend_dep`
				VALUES
				#Enter route, and minutes on the hour it leaves
				(id+1, @routeFromNewStation, @startHour + 0),
				(id+2, @routeFromNewStation, @startHour + 0.30);
				
			SET id = id +2;
			SET @startHour = @startHour+1;
			SET counter = counter +1;
		END WHILE;
	SET @startHour = @startHour-@journeyHours;
	END$$
DELIMITER ;



DELIMITER $$
CREATE PROCEDURE currStat1()
	BEGIN
		
		DECLARE counter INT DEFAULT 0;
		DECLARE id INT DEFAULT (SELECT MAX(depNo) FROM `t8005t3`.`final_metro_week_dep`);

		WHILE counter<(@journeyHours)  DO
			INSERT INTO `t8005t3`.`final_metro_weekend_dep`
				VALUES
				(id+1, @routeFromCurrentStation, @startHour + 0.15),
				(id+2, @routeFromCurrentStation, @startHour + 0.45);
                                                                
			SET id = id +2;
			SET @startHour = @startHour +1;
			SET counter = counter +1;
		END WHILE;
	END$$
DELIMITER ;

call newStat1();
call currStat1();