SET @station = 'Airport';

DELETE FROM `t8005t3`.`final_metro_trainstations`
WHERE stationName = @station;
