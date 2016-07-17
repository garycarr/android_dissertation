
<?php

session_start();

set_time_limit(120);

define('I',100000); // J. define infinite distance

/**
 * class to Build Dijkstra model
 * To build the class
 * Use int to index all the points on the map
 * @param int startPoint
 * @param array routes[] = array($startPoint,$endPoint,$distance)
 * @author Rick.purple
*/


/**
 * J. General points, the algorithm takes in an array from a database, in my version of the php this was the routes and takes the form of a (A,B,C) where
 *A is the starting node (e.g newcastle) and B is the destination node (e.g london) and C represents the cost. For example (1, 3, 10)
 *represent an path from node A to node B which costs 10 (as distance). In order to use the algorithm in bidirectional paths, we add for each entry (A, B, C),
 *  another entry (B, A, D) which represents the cost of traveliing between B and A. These entries are populated by querying the database. Here the array
 * aRoutes' represents this array, but in our code this is simply Currently, we are interested in the special case where
 * we have one starting node and multiple destination nodes although the algorithm is generic enough to be used to calculate the shortest distances
 * from a set of starting nodes to a set of destination nodes. So for example this is concerned with just one starting point, which could be any of the
 * train stations we have, e.g. Gateshead, Airport. Therefore the starting point is based on which station the user wants to start from on the website.

 Remember that $aRoutes represents a generic array, in our version we replace this with $routes which as mentioned before is queried from the database (specifically the routes table, which you've now updated to final_metro_cost.)

*/
class Dijkstra{
	private $startingStation; // J. starting point
	private $aRoutes = array(); // all possible routes between each two points (single direction)
	private $allPointsMaybe = array(); // all the points on the map
	private $arrayForStartingStation = array();   //J. represents the starting point nodes for example airport(as an array)
	// J.The algorithm is generic enough to accept a set of starting nodes and a set of destination nodes
	private $arrayForEndingStation = array();  // J. represents the destination point nodes for example monument(as an array)
	private $aDistances = array(); // the closest distance from start point to each points
	private $aPathes = array(); // path from each points to its neibor on the best path to the start point
	private $aFullPathes; // path from start point to each points

	/**
	 * Build `ra model, find best path and closest distance from start point to each point on the map
	 * @return null
	 * @param object $startingStation
	 * @param object $aRoutes
	 */
	public function __construct($startingStation,$aRoutes){ //J. The class constructer
		// here we take the startingStation and the aRoutes array, in our code this is represented by
		// $oDijk = new Dijkstra($fromCity,$routes); (found on line 74 in your results.php (if viewing in notepad ++))
		$this->aRoutes = $aRoutes; // J. Assign the internal variable aRoutes to the given aRoutes parameter (array)


		$this->startingStation = $startingStation; //J. Assign the startingStation to the given startingStation parameter

		foreach($aRoutes as $aRoute){ //J. Iterate through the different routes
			if(!in_array($aRoute[0],$this->allPointsMaybe)){
				$this->allPointsMaybe[] = $aRoute[0];
			}
			if(!in_array($aRoute[1],$this->allPointsMaybe)){
				$this->allPointsMaybe[] = $aRoute[1];
			}
		}

		$this->arrayForStartingStation = array($startingStation);// J. represents the statrting point node
		$this->arrayForEndingStation = $this->array_remove($this->allPointsMaybe, $startingStation); // represnts all destination nodes

		foreach($this->arrayForEndingStation as $intPoint){
			$this->aDistances[$intPoint] = I; // J. intialize the distance between each destination node and the starting node to infinite value (defined on line 7)
		}
		$this->aDistances[$startingStation] = 0;	// J. The distance from the starting node to itself

		$this->findPath(); //J. find path method, full method shown on line 131
	}

	/**
	 * function to get the best path
	 * @return pathes for each node on the map
	 */
	public function getPath(){
		foreach($this->allPointsMaybe as $intPoint){
			$this->fillFullPath($intPoint,$intPoint);// J. Finds the full path between each destination node and the starting node without passing through any intermediary nodes, method shown on line 185
		}
		return $this->aFullPathes; // J. method on line 204 under an if  condition and on line 220 under an else condition
	}

	/**
	 * function to get the closest distance
	 * @return
	 */
	public function getDistance(){
		return $this->aDistances; // J. return the array representing the closest distance from start point to each points
	}

	/**
	 * Remove specified element from array
	 * @return array
	 * @param array $arr : array to be processing
	 * @param array $value : the element to be remove from the array
	 */
	private function array_remove($arr,$value) {
		return array_values(array_diff($arr,array($value))); // J. remove the element $value from the array $arr, this called on
		//line 188 (explained further below)
		//,
	}

	/**
	 * Dijkstra algorithm implementation
	 * @return null
	 */
	private function findPath(){
		// J.method to find path, called on line 118
		while(!empty($this->arrayForEndingStation)){
			$intShortest = I;// J. represents a very big value (defined on line 7) to be compared with the short distance value we get in each iteration. If the calculated short distance is smaller than this big value, we consider the calculated value as the shortest distance
			foreach($this->arrayForStartingStation as $intRed){ // J. Iterate through the $arrayForStartingStation array. Each element of the array $arrayForStartingStation is saved in the $intRed variable

				# find possible rounte
				foreach($this->aRoutes as $aRoute){ // J. Iterate through the $aRoutes array. Each element of the array $aRoutes is saved in the $aRoute variable
					// J.$aRoute represents one possible route from one start node to destination node
					if($intRed == $aRoute[0]){ // J. We found one route which has a starting point equal to $intRed
						//J. $aRoute[0] is the start point
						//J. $aRoute[1] is the destination point
						//J. $aRoute[2] is the cost

						$aDis[$intRed][$aRoute[1]] = $aRoute[2];// J. The distance between the starting point and the destination node is equal to the "cost" specified by $aRoute[2]
						//	echo "Enters here with aroute </center>";
						//print_r($aRoute);
						//echo "<br/></center>";
						//echo " and intRed </center>";
						//print_r($intRed);
						//	echo "<br/></center>";
							
						# rewrite distance
						// J. aDistances[$intRed] means the shortest distance from the starting node $intRed to itself. This has been put to a very big value
						$intDistance = $this->aDistances[$intRed] + $aRoute[2];
						if($this->aDistances[$aRoute[1]] > $intDistance){// J. If we find that the distance between the starting node and the destination node is greater than the previous distance, we keep the previous distance as the shortest distance.
							$this->aDistances[$aRoute[1]] = $intDistance;
							# change the path
							if($intRed==$this->startingStation ||$intRed==$aRoute[1]){
							}
							else{
								$this->aPathes[$aRoute[1]] = $intRed; // J. add the destination node to the aPathes array
							}
						}

						# find the nearest	neighbor
						// J. If the current destination point is not among the starting nodes and the cost is less than the shortest distance, then recalculate the shortest distance and assign the $intAddPoint variable to the destination node to be used later on.
						// J. At the beginning the $intShortest is assigned to a very big value such as 1000000 (shown on line 7)
						// J. If we find a distance less than that big value, we consider making the $intShortest as the found one.
						// J. For example, if we find a distance 250, then the $intShortest will be 250 (smaller than 1000000).
						// J. But if $intShortest was 10 (instead of 1000000) for example, and we find a distance 250, we cannot assume that the shortest is 250 as the shortest distance will be 10 in this case which does not make sense! this function is here to ensure that if this does happen we add it to the routes.

						if(!in_array($aRoute[1],$this->arrayForStartingStation)&&$aRoute[2]<$intShortest){
							$intShortest = $aRoute[2];
							$intAddPoint = $aRoute[1];
						}
					}
				}
			}

			$this->arrayForStartingStation[] = $intAddPoint; //J. this is the starting point
			$this->arrayForEndingStation = $this->array_remove($this->arrayForEndingStation, $intAddPoint);//J. this is make sure that the destination point
			// J. cannot be the starting point as well, otherwise there is not journey, method declared on line 103
		}
	}

	/**
	 * mid step function to find full path from start point to the end point.
	 * @return null
	 * @param int $intEndPoint
	 * @param int $intMidPoint
	 */
	private function fillFullPath($intEndPoint,$intMidPoint){ // J. method to fill the path between the an intermediary point and the destination point (called on line 83)

		if(isset($this->aPathes[$intMidPoint])){ // J. If the mid (intermediary) node has pathes passing through it
			// add the path passing through the mid node to the full paths ending by the destination node, basically the paths between the intermediary nodes and the destination node (e.g Airport is starting node, Monument, Gosforth are Intermediary nodes and Gateshead is the destination node)
			$this->aFullPathes[$intEndPoint][] = $this->aPathes[$intMidPoint];
			// J. after we have found the path from the intermediary node to the destinationation node
			// we add this path to the set of the paths arriving to the destination node
			// below we keep repeating filling the full path to the destination node starting from the mid node.
			// for example if there are three intermidary nodes, this is done three times.
			// this is is called through the getPath on line 96
			$this->fillFullPath($intEndPoint,$this->aPathes[$intMidPoint]);

		}

		else{
			$this->aFullPathes[$intEndPoint][] = $this->startingStation; //J. this is an else
			// in case we don't have any intermidiary nodes, used on line 96

		}
	}
}

?>