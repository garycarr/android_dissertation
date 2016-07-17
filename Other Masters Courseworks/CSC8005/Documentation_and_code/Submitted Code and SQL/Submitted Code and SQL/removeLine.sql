SET @route1 = 'NSh-Whi';
SET @route2 = 'Whi-NSh';

DELETE FROM `t8005t3`.`final_metro_cost`
WHERE pathID in (@route1, @route2);