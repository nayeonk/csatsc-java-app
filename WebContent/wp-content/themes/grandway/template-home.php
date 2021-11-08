<?php # Template Name: Home ?>

<?php get_header(); ?>

<?php if (have_posts()) : while (have_posts()) : the_post(); ?>

<script>
	var allCamps = document.getElementById("camps");
	//convert table into an array
	var rows = allCamps.rows, rlen = rows.length, arr = new Array(), r, j, cells, clen;
	for(r = 0; r < rlen; r++){
		cells = rows[r].cells;
		clen = cells.length;
		arr[r] = new Array();
		for(j = 0; j < clen; j++){
			arr[r][j] = cells[j].innerHTML;
		}
		// keeping rows
		arr[r][clen] = r;
	}
	for(r = 0; r < rlen; r++){
		arr[r].pop();
	}
	var asc1=1, asc2=1, asc3=1, asc4=1, asc5=1, asc6=1;
	description_array = new Array();
	var initialSetup = false

	function updateFilter(){
		if (!initialSetup) {
			allCamps = document.getElementById("camps");
			//convert table into an array
			rows = allCamps.rows, rlen = rows.length, arr = new Array(), r, j, cells, clen;
			for(r = 0; r < rlen; r++){
				cells = rows[r].cells;
				clen = cells.length;
				arr[r] = new Array();
				for(j = 0; j < clen; j++){
					arr[r][j] = cells[j].innerHTML;
				}
				// keeping rows
				arr[r][clen] = r;
			}
			for(r = 0; r < rlen; r++){
				arr[r].pop();
			}
			asc1=1, asc2=1, asc3=1, asc4=1, asc5=1, asc6=1;
			description_array = new Array();
			initialSetup = true
		}
		
		var e = document.getElementById("classselect");
		var classOption = e.options[e.selectedIndex].value;

		var e = document.getElementById("diffselect");
		var diffOption = e.options[e.selectedIndex].value;

		var e = document.getElementById("gradeselect");
		var gradeOption = e.options[e.selectedIndex].value;

		var e = document.getElementById("startdateselect");
		var startDateOption = e.options[e.selectedIndex].value;

		description_array = new Array();
		var d=0;
		<?php foreach($classesByDate as $class): ?>
			description_array[d] = "<?php echo ($class["description"]) ?>";
			d++;
		<?php endforeach; ?>

		// filters the array based on the above values
		var filteredList = arr.filter(function(row){
            var classTopic = ($.parseHTML(row[0]))[1].getAttribute('hash');
			if (classTopic !== classOption && classOption !== 'all'){
				return false;
			}

            var campLevel = ($.parseHTML(row[1]))[1].getAttribute('hash');
			if (campLevel !== diffOption && diffOption !== 'all') {
				console.log("in false");
				return false;
			}

			gradeOption = parseInt(gradeOption);
			var gradeRange = ($.parseHTML(row[2]))[1];
			// console.log(gradeRange);
			var firstgrade = gradeRange.getAttribute('firstgrade');
			var lastgrade = gradeRange.getAttribute('lastgrade');
			// console.log(firstgrade, lastgrade);
			if ((gradeOption < firstgrade || gradeOption > lastgrade) && gradeOption !== 0) {
				return false;
			}

            var startDate = ($.parseHTML(row[3]))[1].getAttribute('hash');
			if (startDate !== startDateOption && startDateOption !== 'all') {
				return false;
			}

			return true;
		});

		newRowPos = new Array();
		//format cells
		rlen = filteredList.length;
		for(r = 0; r < rlen; r++){
			//grab row position in last cell
			newRowPos[r] = filteredList[r][clen];
			//get rid of the last cell

			//format cells
			filteredList[r] = " <td>"+filteredList[r].join("</td> <td>")+"</td>";
		}

		//match description to the right row, based on new row position
		newDescriptions = new Array();
		var oldDescPos = 0;
		for(r=0; r<rlen; r++){
			oldDescPos = newRowPos[r];
			newDescriptions[r] = description_array[oldDescPos];
		}

		//format rows
		var s = "";
		for (r = 0; r < rlen; r++)
		{
			s = s + '<tr id="row' + r + '" onclick="showPopup(\'row' + r + '\');" style="cursor:pointer;">' + filteredList[r] + '</tr>';
		}

		camps.innerHTML = s;
	}

	//Show/Format pop up dialog box
	function showPopup(rowId){
        console.log("rowId: ", rowId);
        var row = document.getElementById(rowId);
        console.log("row: ", row);
        console.log(row.childNodes[1]);
        var description = row.childNodes[1].childNodes[1].getAttribute("description");
        console.log("description: ", description);
		$("#popup").dialog({
			width: 500,
			height: 300,
			open: $("#popup").html(description) 
		});
	}

	function sortByColumn(tbody, col, asc){
		var rows = tbody.rows, rlen = rows.length, arr = new Array(), r, j, cells, clen;
		description_array = new Array();
		var d=0;
		<?php foreach($classesByDate as $class): ?>
			description_array[d] = "<?php echo ($class["description"]) ?>";
			d++;
		<?php endforeach; ?>
		//convert table into an array
		for(r = 0; r < rlen; r++){
			cells = rows[r].cells;
			clen = cells.length;
			arr[r] = new Array();
			for(j = 0; j < clen; j++){
				arr[r][j] = cells[j].innerHTML;
			}
			//keeping track of original row position
			arr[r][clen] = r;
		}

		// sort the array by the specified column number (col) and order (asc)
		arr.sort(function(a, b){
            var divA = ($.parseHTML(a[col]))[1];
            // console.log(divA);
			var hashA = divA.getAttribute('hash');
            if (!isNaN(hashA)) {
                hashA = parseInt(hashA);
            }
            var divB = ($.parseHTML(b[col]))[1];
			var hashB = divB.getAttribute('hash');
            if (!isNaN(hashB)) {
                hashB = parseInt(hashB);
            }
            // console.log(hashA, hashB);
			return (hashA == hashB) ? 0 : ((hashA > hashB) ? asc : -1*asc);
		});

		newRowPos = new Array();
		//format cells
		for(r = 0; r < rlen; r++){
			//grab row position in last cell
			newRowPos[r] = arr[r][clen];
			//get rid of the last cell
			arr[r].pop();
			//format cells
			arr[r] = " <td>"+arr[r].join("</td> <td>")+"</td>";
		}

		//match description to the right row, based on new row position
		newDescriptions = new Array();
		var oldDescPos = 0;
		for(r=0; r<rlen; r++){
			oldDescPos = newRowPos[r];
			newDescriptions[r] = description_array[oldDescPos];
		}

		//format rows
		var s = "";
		for (r = 0; r < rlen; r++)
		{
			s = s + '<tr id="row' + r + '" onclick="showPopup(\'row' + r + '\');" style="cursor:pointer;">' + arr[r] + '</tr>';
		}

		camps.innerHTML = s;
	}

</script>

<div id="page-<?php the_ID(); ?>" class="begin-content general-font-area">
    <div class="container">
      	<?php the_content(); ?>
		<?php $str = file_get_contents(get_home_url() . '/SummerCamp/api/camps');
		function console_log($output, $with_script_tags = true) {
    			$js_code = 'console.log(' . json_encode($output, JSON_HEX_TAG) . ');';
    			if ($with_script_tags) {
        			$js_code = '<script>' . $js_code . '</script>';
    			}
    			echo $js_code;
		}
		$classesByDate = json_decode($str, true);
		$classesByDate = $classesByDate["camps"];
		console_log($classesByDate)
		
// 		$resources = json_decode($str, true);
// 		    $resources = $resources["camps"];
// 			$classesByDate = array();
// 			foreach ($resources as $rkey => $resource){
// 				if (convertDateToFormat($resource['startDate']) == 'Jul 10, 2017 12:00:00 AM'){
// 					$classesByDate[] = $resource;
// 				}
// 			}
		?>
		<br />

		<h2>Available Classes</h2>
		<h4>
			The costs listed below do not account for generous scholarships that are available for merit and need. When applying, make sure to submit the most recent report card and/or proof of free/reduced meals to apply for the scholarships.
		</h4>
		<br>
		<h4>
			<a href="https://summercamp.usc.edu/class-descriptions/" target="_blank">Click here</a> to see a description of all classes offered.
		</h4>
		<br>

		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/base/jquery-ui.css"/>
        <script src="https://code.jquery.com/jquery-2.1.0.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>


        <!-- These functions convert all of the data received from the api into readable format before 
        	echoing to the page -->
        <?php
			function convertGradeToString($grade) {
				$gradeLabel = "";
				switch($grade) {
					case 1:
						$gradeLabel = "Pre-Kindergarten";
						break;
					case 2:
						$gradeLabel = "Kindergarten";
						break;
					case 15:
						$gradeLabel = "College";
						break;
					case 16:
						$gradeLabel = "Adult";
						break;
					case 17:
						$gradeLabel = "Elementary Teachers";
						break;
					case 18:
						$gradeLabel = "Middle School Teachers";
						break;
					case 19:
						$gradeLabel = "High School Teachers";
						break;
					case 3:
						$gradeLabel = "1st";
						break;
					case 4:
						$gradeLabel = "2nd";
						break;
					case 5:
						$gradeLabel = "3rd";
						break;
					default:
						$grade = $grade - 2;
						$gradeLabel = "{$grade}th";
						break;
				}
				return $gradeLabel;
			};

			function convertGradeRangeToString($gradeRange) {
				$split = explode('-', $gradeRange);
				$startGradeLabel = convertGradeToString($split[0]);
				$endGradeLabel = convertGradeToString($split[1]);
				return "{$startGradeLabel} - {$endGradeLabel}";
			};
		
			function debug_to_console( $data ) {
				$output = $data;
				if ( is_array( $output ) )
					$output = implode( ',', $output);

				echo "<script>console.log( 'Debug Objects: " . $output . "' );</script>";
			}

			function convertDateToFormat($date_string) {
				if ($date_string) {
					$date = date_create($date_string);
					return date_format($date, 'F j, Y');
				} else {
					return "n/a";
				}
			};


			function convertTimeToFormat($time_string) {
				if ($time_string) {
					$time = date_create($time_string);
					return date_format($time, 'h:i a');
				} else {
					return "n/a";
				}
			};
		
			function convertDateToCompareFormat($date_string) {
				if ($date_string) {
					$date = date_create($date_string);
					return date_format($date, 'Y-m-j');
				} else {
					return "n/a";
				}
			};

            function hashCampTopic($campTopic) {
                return $campTopic;
            };

//             function hashCampLevel($campLevel) {
//                 switch ($campLevel) {
//                     case "Beginning":
//                         return 1;
//                     case "Intermediate":
//                         return 2;
//                     case "Advanced":
//                         return 3;
//                     default:
//                         return -1;
//                 }
//             };
		
			 function hashCampLevel($campLevel) {
                switch ($campLevel) {
                    case "Level 1":
                        return 1;
                    case "Level 2":
                        return 2;
                    case "Level 3":
                        return 3;
					case "Level 4":
                        return 4;
					case "Level 5":
                        return 5;
                    default:
                        return -1;
                }
            };

            function hashGradeRange($firstGrade, $lastGrade) {
                return 19*intval($firstGrade) + intval($lastGrade);
            };

            function hashDate($date_string) {
				if ($date_string) {
					$date = date_create($date_string);
					return date_format($date, 'U');
				} else {
					return "n/a";
				}
            };
		
			function convertRemoteToString($remoteValue) {
				if ($remoteValue == 1) {
					return "Yes";
				} else {
					return "No";
				}
			}
		

            //
			// function convertPriceToFormat($cost) {
			// 	if ($cost) {
			// 		$dollars = floor($cost / 100);
			// 		$cents = floor($cost % 100) * 0.01;
			// 		$total = $dollars + $cents;
			// 		return money_format('$%.2n', $total);
			// 	} else {
			// 		return "Free";
			// 	}
			// };
		?>
		<!--<button data-toggle="collapse" data-target="#filter-container">Filters</button>-->
		<!-- *Each camp is $240, but merit/need based scholarships are available. -->
		<div id="filter-container">
        <?php
		/* Make filters that show up at the top */

		// class topics filter
        echo "<div class=\"select-filter\">";
        echo "<h4> Class Name </h4>";
		$uniqueclasses = array();
		foreach ($classesByDate as $class):
			array_push($uniqueclasses, $class["campTopic"]);
		endforeach;

		$finalclasses = array_unique($uniqueclasses);
		echo "<select onchange=\"updateFilter();\" class=\"classlist\" id=\"classselect\">";
		echo "<option value=\"all\">&nbsp;All</option>";
        foreach($finalclasses as $campTopic):
        	echo "<option value=\"" . hashCampTopic($campTopic) . "\">&nbsp;" . $campTopic . "</option>";
	    endforeach;
	    echo "</select>";
	    echo "</div>";

		// difficulty filter
	    echo "<div class=\"select-filter\">";
	    echo "<h4> Difficulty </h4>";
	    $uniqueclasses = array();
		foreach ($classesByDate as $class):
			array_push($uniqueclasses, $class["campLevel"]);
		endforeach;

		$finalclasses = array_unique($uniqueclasses);

		echo "<select onchange=\"updateFilter();\" class=\"classlist\" id=\"diffselect\">";
		echo "<option value=\"all\">&nbsp;All</option>";
        foreach($finalclasses as $campLevel):
        	echo "<option value=\"" . hashCampLevel($campLevel) . "\">&nbsp;" . $campLevel . "</option>";
	    endforeach;
	    echo "</select>";
	    echo "</div>";

		// grade filter
	    echo "<div class=\"select-filter\">";
	    echo "<h4> Grade </h4>";
	    $uniqueclasses = array();
		foreach ($classesByDate as $class):
			$split = explode('-', $class["recommendedGrade"]);
			$firstGrade = $split[0];
			$lastGrade = $split[1];
			for ($i = $firstGrade; $i <= $lastGrade; $i++) {
				array_push($uniqueclasses, $i);
			}
		endforeach;

		$finalclasses = array_unique($uniqueclasses);
		echo "<select onchange=\"updateFilter();\" class=\"classlist\" id=\"gradeselect\">";
		echo "<option value=\"0\">&nbsp;All</option>";
        foreach($finalclasses as $grade):
        	echo "<option value=\"" . $grade . "\">&nbsp;" . convertGradeToString($grade) . "</option>";
	    endforeach;
	    echo "</select>";
	    echo "</div>";

		// start date filter
	    echo "<div class=\"select-filter\">";
	    echo "<h4> Start Date </h4>";
	    $uniqueclasses = array();
		foreach ($classesByDate as $class):
			$campEndDate = new DateTime(convertDateToCompareFormat($class["endDate"]));
			$nowDate = new DateTime("now");
			if($campEndDate > $nowDate):
				array_push($uniqueclasses, $class["startDate"]);
			endif;
		endforeach;
		$finalclasses = array_unique($uniqueclasses);
		echo "<select onchange=\"updateFilter();\" class=\"classlist\" id=\"startdateselect\">";
		echo "<option value=\"all\">&nbsp;All</option>";
        foreach($finalclasses as $date):
        	echo "<option value=\"" . hashDate($date) . "\">&nbsp;" . convertDateToFormat($date) . "</option>";
	    endforeach;
	    echo "</select>";
	    echo "</div>";
	    ?>
	    </div>

    <!-- ************Pop-Up*************-->
    <!-- Uncomment this code if you would like a pop up with a course description. 
		 Took it out because descriptions were not working. -->
	<!--
        <div id="popup" class="dialog" title="Course Description">
            <p></p>
        </div> 
     -->

		<?php if ((count($classesByDate)) > 0): ?>
		<!-- ***********************TABLE************************** -->
		<table class="table table-striped">
			<thead>
				<tr>
					<th class="special_tds" onclick="sortByColumn(camps, 0, asc1); asc1 *= -1; asc2=1; asc3=1; asc4=1; asc5=1; asc6=1;" style="cursor:pointer;">Class Name</th>
					<th class="special_tds" onclick="sortByColumn(camps, 1, asc2); asc2 *= -1; asc1=1; asc3=1; asc4=1; asc5=1; asc6=1;" style="cursor:pointer;">Difficulty</th>
					<th class="special_tds" onclick="sortByColumn(camps, 2, asc3); asc3 *= -1; asc1=1; asc2=1; asc4=1; asc5=1; asc6=1;" style="cursor:pointer;">Grade</th>
					<th class="special_tds" onclick="sortByColumn(camps, 3, asc4); asc4 *= -1; asc1=1; asc2=1; asc3=1; asc5=1; asc6=1;" style="cursor:pointer;">Start Date</th>
					<th class="special_tds" onclick="sortByColumn(camps, 4, asc5); asc5 *= -1; asc1=1; asc2=1; asc3=1; asc4=1; asc6=1;" style="cursor:pointer;" >End Date</th>
					<!--REGISTER NOW COLUMN TODO: Replace-->
					<!--<th>Register Now!</th>-->
					<th class="special_tds" style="white-space:nowrap;">Days</th>
					<th class="special_tds" style="white-space:nowrap;">Start Time</th>
					<th class="special_tds" style="white-space:nowrap;">End Time</th>

					<th class="special_tds">Remote</th>
					<th class="special_tds">Price</th>
				</tr>
			</thead>

			<tbody id="camps">
				<?php for ($r = 0; $r < count($classesByDate); $r++):
                    $class = $classesByDate[$r]; ?>
				<?php 
				console_log($class);
				console_log($r);
				$campEndDate = new DateTime(convertDateToCompareFormat($class["endDate"]));
				$nowDate = new DateTime("now");
				if($campEndDate >= $nowDate):
				?>
				<tr style="cursor:pointer;" id="<?php echo "row$r"; ?>" onclick="showPopup('<?php echo "row$r"; ?>');">
					<td>
                        <?php
                        echo "<div description=\"" . $class["description"] . "\" ";
                        echo "hash=\"" . hashCampTopic($class["campTopic"]) . "\">";
                        echo ($class["campTopic"] ?: "n/a");
                        echo "</div>";
                        ?>
                    </td>
					<td>
                        <?php
                        $hash = hashCampLevel($class["campLevel"]);
                        echo "<div hash=\"" . $hash . "\">";
                        echo ($class["campLevel"] ?: "n/a");
                        echo "</div>";
                        ?>
                    </td>
					<td>
                        <?php
						$split = explode('-', $class["recommendedGrade"]);
						echo "<div firstgrade=\"" . $split[0] . "\" ";
						echo "lastgrade=\"" . $split[1] . "\" ";
                        echo "hash=\"" . hashGradeRange($split[0], $split[1]) . "\">";
						echo convertGradeRangeToString($class["recommendedGrade"] ?: "n/a");
						echo "</div>";
					    ?>
                    </td>
					<td>
                        <?php
                        $hash = hashDate($class["startDate"]);
                        echo "<div hash=\"" . $hash . "\">";
                        echo convertDateToFormat($class["startDate"]);
                        echo "</div>";
                        ?>
                    </td>
					<td style="white-space:nowrap;">
                        <?php
                        $hash = hashDate($class["endDate"]);
                        echo "<div hash=\"" . $hash . "\">";
                        echo convertDateToFormat($class["endDate"]);
                        echo "</div>";
                        ?>
                    </td>

                    <td style="white-space:nowrap;">
						<?php
						echo "<div>";
						echo $class['days'];
						echo "</div>";
						?>
					</td>

                    <td style="white-space:nowrap;">
						<?php
						echo "<div>";
						echo convertTimeToFormat($class["startTime"]);
						echo "</div>";
						?>
					</td>
					<td style="white-space:nowrap;">
						<?php
						echo "<div>";
						echo convertTimeToFormat($class["endTime"]);
						echo "</div>";
						?>
					</td>
					<td>
						<?php
						echo "<div>";
						echo convertRemoteToString($class["remote"]);
						echo "</div>";
						?>
					</td>
					<td>
						<?php
						echo "<div>";
						echo "$";
						echo strval($class["price"] / 100.0);
						echo "</div>";
						?>
					</td>
				</tr>
				<?php 
				endif;
				?>				
				<?php endfor; ?>
			</tbody>
		</table>

		<?php else: ?>
			<h6>Sorry, no camps are currently available. Check back soon.</h6>
		<?php endif; ?>

		</div>
    </div>

  <?php endwhile; ?>

  <?php endif; ?>

<?php get_footer(); ?>

<!-- <script>
	var allCamps = document.getElementById("camps");
	//convert table into an array
	var rows = allCamps.rows, rlen = rows.length, arr = new Array(), r, j, cells, clen;
	for(r = 0; r < rlen; r++){
		cells = rows[r].cells;
		clen = cells.length;
		arr[r] = new Array();
		for(j = 0; j < clen; j++){
			arr[r][j] = cells[j].innerHTML;
		}
		// keeping rows
		arr[r][clen] = r;
	}
	for(r = 0; r < rlen; r++){
		arr[r].pop();
	}
	var asc1=1, asc2=1, asc3=1, asc4=1, asc5=1, asc6=1;
	description_array = new Array();

	function updateFilter(){
		var e = document.getElementById("classselect");
		var classOption = e.options[e.selectedIndex].value;

		var e = document.getElementById("diffselect");
		var diffOption = e.options[e.selectedIndex].value;

		var e = document.getElementById("gradeselect");
		var gradeOption = e.options[e.selectedIndex].value;

		var e = document.getElementById("startdateselect");
		var startDateOption = e.options[e.selectedIndex].value;

		description_array = new Array();
		var d=0;
		<?php foreach($classesByDate as $class): ?>
			description_array[d] = "<?php echo ($class["description"]) ?>";
			d++;
		<?php endforeach; ?>

		// filters the array based on the above values
		var filteredList = arr.filter(function(row){
            var classTopic = ($.parseHTML(row[0]))[1].getAttribute('hash');
			if (classTopic !== classOption && classOption !== 'all'){
				return false;
			}

            var campLevel = ($.parseHTML(row[1]))[1].getAttribute('hash');
			if (campLevel !== diffOption && diffOption !== 'all') {
				return false;
			}

			gradeOption = parseInt(gradeOption);
			var gradeRange = ($.parseHTML(row[2]))[1];
			// console.log(gradeRange);
			var firstgrade = gradeRange.getAttribute('firstgrade');
			var lastgrade = gradeRange.getAttribute('lastgrade');
			// console.log(firstgrade, lastgrade);
			if ((gradeOption < firstgrade || gradeOption > lastgrade) && gradeOption !== 0) {
				return false;
			}

            var startDate = ($.parseHTML(row[3]))[1].getAttribute('hash');
			if (startDate !== startDateOption && startDateOption !== 'all') {
				return false;
			}

			return true;
		});

		newRowPos = new Array();
		//format cells
		rlen = filteredList.length;
		for(r = 0; r < rlen; r++){
			//grab row position in last cell
			newRowPos[r] = filteredList[r][clen];
			//get rid of the last cell

			//format cells
			filteredList[r] = " <td>"+filteredList[r].join("</td> <td>")+"</td>";
		}

		//match description to the right row, based on new row position
		newDescriptions = new Array();
		var oldDescPos = 0;
		for(r=0; r<rlen; r++){
			oldDescPos = newRowPos[r];
			newDescriptions[r] = description_array[oldDescPos];
		}

		//format rows
		var s = "";
		for (r = 0; r < rlen; r++)
		{
			s = s + '<tr id="row' + r + '" onclick="showPopup(\'row' + r + '\');" style="cursor:pointer;">' + filteredList[r] + '</tr>';
		}

		camps.innerHTML = s;
	}

	//Show/Format pop up dialog box
	function showPopup(rowId){
        console.log("rowId: ", rowId);
        var row = document.getElementById(rowId);
        console.log("row: ", row);
        console.log(row.childNodes[1]);
        var description = row.childNodes[1].childNodes[1].getAttribute("description");
        console.log("description: ", description);
		$("#popup").dialog({
			width: 500,
			height: 300,
			open: $("p").html(description)
		});
	}

	function sortByColumn(tbody, col, asc){
		var rows = tbody.rows, rlen = rows.length, arr = new Array(), r, j, cells, clen;
		description_array = new Array();
		var d=0;
		<?php foreach($classesByDate as $class): ?>
			description_array[d] = "<?php echo ($class["description"]) ?>";
			d++;
		<?php endforeach; ?>
		//convert table into an array
		for(r = 0; r < rlen; r++){
			cells = rows[r].cells;
			clen = cells.length;
			arr[r] = new Array();
			for(j = 0; j < clen; j++){
				arr[r][j] = cells[j].innerHTML;
			}
			//keeping track of original row position
			arr[r][clen] = r;
		}

		// sort the array by the specified column number (col) and order (asc)
		arr.sort(function(a, b){
            var divA = ($.parseHTML(a[col]))[1];
            // console.log(divA);
			var hashA = divA.getAttribute('hash');
            if (!isNaN(hashA)) {
                hashA = parseInt(hashA);
            }
            var divB = ($.parseHTML(b[col]))[1];
			var hashB = divB.getAttribute('hash');
            if (!isNaN(hashB)) {
                hashB = parseInt(hashB);
            }
            // console.log(hashA, hashB);
			return (hashA == hashB) ? 0 : ((hashA > hashB) ? asc : -1*asc);
		});

		newRowPos = new Array();
		//format cells
		for(r = 0; r < rlen; r++){
			//grab row position in last cell
			newRowPos[r] = arr[r][clen];
			//get rid of the last cell
			arr[r].pop();
			//format cells
			arr[r] = " <td>"+arr[r].join("</td> <td>")+"</td>";
		}

		//match description to the right row, based on new row position
		newDescriptions = new Array();
		var oldDescPos = 0;
		for(r=0; r<rlen; r++){
			oldDescPos = newRowPos[r];
			newDescriptions[r] = description_array[oldDescPos];
		}

		//format rows
		var s = "";
		for (r = 0; r < rlen; r++)
		{
			s = s + '<tr id="row' + r + '" onclick="showPopup(\'row' + r + '\');" style="cursor:pointer;">' + arr[r] + '</tr>';
		}

		camps.innerHTML = s;
	}

</script> -->
