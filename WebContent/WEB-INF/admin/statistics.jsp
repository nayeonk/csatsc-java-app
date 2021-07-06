<%@ page language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%-- Make all relative href links relative to SummerCamp since root is used by WordPress --%>
<base href="SummerCamp"/>

<t:adminpage>
    <jsp:attribute name="styles">
        <link rel="stylesheet" type="text/css" href="css/statistics.css"/>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="scripts/tableCsvExporter.js"></script>
    </jsp:attribute>

    <jsp:body>
        <div id="container" class="container" style="margin: 15px">

            <div class="statistics-panel">
                <div class="statistics-panel-body">
                    <form id="form" action="/SummerCamp/statistics" method="POST" name="statisticsform">
                        <hr>
                        <select id="year_dropdown" name="year">
                            <option name="year" value="none"> Year</option>
                            <option
                                    <c:if test="${subcategory.year == '2016'}">selected</c:if> name="year" value="2016">
                                2016
                            </option>
                            <option
                                    <c:if test="${subcategory.year == '2017'}">selected</c:if> name="year" value="2017">
                                2017
                            </option>
                            <option
                                    <c:if test="${subcategory.year == '2018'}">selected</c:if> name="year" value="2018">
                                2018
                            </option>
                            <option
                                    <c:if test="${subcategory.year == '2019'}">selected</c:if> name="year" value="2019">
                                2019
                            </option>
                            <option
                                    <c:if test="${subcategory.year == '2020'}">selected</c:if> name="year" value="2020">
                                2020
                            </option>
                        </select>
                        <select id="gender_dropdown" name="gender">
                            <option name="gender" value="none">Gender</option>
                            <option
                                    <c:if test="${subcategory.gender == 'male'}">selected</c:if> name="gender"
                                    value="male">Male
                            </option>
                            <option
                                    <c:if test="${subcategory.gender == 'female'}">selected</c:if> name="gender"
                                    value="female">Female
                            </option>
                            <option
                                    <c:if test="${subcategory.gender == 'DidNotIdentify'}">selected</c:if> name="gender"
                                    value="DidNotIdentify">Did not Identify
                            </option>
                        </select>
                        <select name="ethnicity">
                            <option name="ethnicity" value="none">Ethnicity</option>
                            <option
                                    <c:if test="${subcategory.ethnicity == 'african_american'}">selected</c:if>
                                    name="ethnicity" value="african_american">African-American
                            </option>
                            <option
                                    <c:if test="${subcategory.ethnicity == 'american_indian'}">selected</c:if>
                                    name="ethnicity" value="american_indian">American-Indian
                            </option>
                            <option
                                    <c:if test="${subcategory.ethnicity == 'asian'}">selected</c:if> name="ethnicity"
                                    value="asian">Asian
                            </option>
                            <option
                                    <c:if test="${subcategory.ethnicity == 'caucasian'}">selected</c:if>
                                    name="ethnicity" value="caucasian">Caucasian
                            </option>
                            <option
                                    <c:if test="${subcategory.ethnicity == 'hispanic'}">selected</c:if> name="ethnicity"
                                    value="hispanic">Hispanic
                            </option>
                            <option
                                    <c:if test="${subcategory.ethnicity == 'other'}">selected</c:if> name="ethnicity"
                                    value="other">Other
                            </option>
                        </select>
                        <select name="income">
                            <option name="income" value="none">Income</option>
                            <option
                                    <c:if test="${subcategory.income == '0_to_20'}">selected</c:if> name="income"
                                    value="0_to_20">0-20,000
                            </option>
                            <option
                                    <c:if test="${subcategory.income == '20_to_40'}">selected</c:if> name="income"
                                    value="20_to_40">20,001-40,000
                            </option>
                            <option
                                    <c:if test="${subcategory.income == '40_to_60'}">selected</c:if> name="income"
                                    value="40_to_60">40,001-60,000
                            </option>
                            <option
                                    <c:if test="${subcategory.income == '60_to_80'}">selected</c:if> name="income"
                                    value="60_to_80">60,001-80,000
                            </option>
                            <option
                                    <c:if test="${subcategory.income == '80_to_100'}">selected</c:if> name="income"
                                    value="80_to_100">80,001-100,000
                            </option>
                            <option
                                    <c:if test="${subcategory.income == 'over_100'}">selected</c:if> name="income"
                                    value="over_100">100,001
                            </option>
                            <option
                                    <c:if test="${subcategory.income == 'no_income_reported'}">selected</c:if>
                                    name="income" value="no_income_reported">NA
                            </option>
                        </select>
                        <select name="grade">
                            <option name="grade" value="none">Grade</option>
                            <option
                                    <c:if test="${subcategory.grade == 'K'}">selected</c:if> name="grade" value="K">
                                Kindergarten
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == '1'}">selected</c:if> name="grade" value="1">1st
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == '2'}">selected</c:if> name="grade" value="2">2nd
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == '3'}">selected</c:if> name="grade" value="3">3rd
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == '4'}">selected</c:if> name="grade" value="4">4th
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == '5'}">selected</c:if> name="grade" value="5">5th
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == '6'}">selected</c:if> name="grade" value="6">6th
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == '7'}">selected</c:if> name="grade" value="7">7th
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == '8'}">selected</c:if> name="grade" value="8">8th
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == '9'}">selected</c:if> name="grade" value="9">9th
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == '10'}">selected</c:if> name="grade" value="10">
                                10th
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == '11'}">selected</c:if> name="grade" value="11">
                                11th
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == '12'}">selected</c:if> name="grade" value="12">
                                12th
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == 'college'}">selected</c:if> name="grade"
                                    value="college">College
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == 'adults'}">selected</c:if> name="grade"
                                    value="adults">Adults
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == 'elementary_teachers'}">selected</c:if>
                                    name="grade" value="elementary_teachers">Elementary Teachers
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == 'ms_teachers'}">selected</c:if> name="grade"
                                    value="ms_teachers">Middle School Teachers
                            </option>
                            <option
                                    <c:if test="${subcategory.grade == 'hs_teachers'}">selected</c:if> name="grade"
                                    value="hs_teachers">High School Teachers
                            </option>
                        </select>
                        <select name="topic">
                            <option name="topic" value="none">Topic</option>
                            <option
                                    <c:if test="${subcategory.topic == 'scratchjr'}">selected</c:if> name="topic"
                                    value="scratchjr">Scratch Jr.
                            </option>
                            <option
                                    <c:if test="${subcategory.topic == 'scratch'}">selected</c:if> name="topic"
                                    value="scratch">Scratch
                            </option>
                            <option
                                    <c:if test="${subcategory.topic == 'python'}">selected</c:if> name="topic"
                                    value="python">Python
                            </option>
                            <option
                                    <c:if test="${subcategory.topic == 'java'}">selected</c:if> name="topic"
                                    value="java">Java
                            </option>
                            <option
                                    <c:if test="${subcategory.topic == 'scratch_or_jr_java'}">selected</c:if>
                                    name="topic" value="scratch_or_jr_java">Scratch or Scratch Jr. or Java
                            </option>
                            <option
                                    <c:if test="${subcategory.topic == 'robotics_jr'}">selected</c:if> name="topic"
                                    value="robotics_jr">Junior Robotics
                            </option>
                            <option
                                    <c:if test="${subcategory.topic == 'robotics'}">selected</c:if> name="topic"
                                    value="robotics">Robotics
                            </option>
                            <option
                                    <c:if test="${subcategory.topic == 'web_dev'}">selected</c:if> name="topic"
                                    value="web_dev">Web Development
                            </option>
                            <option
                                    <c:if test="${subcategory.topic == 'hour_of_code'}">selected</c:if> name="topic"
                                    value="hour_of_code">Hour of Code
                            </option>
                            <option
                                    <c:if test="${subcategory.topic == 'before_camp_care'}">selected</c:if> name="topic"
                                    value="before_camp_care">Before Camp Care
                            </option>
                            <option
                                    <c:if test="${subcategory.topic == 'after_camp_care'}">selected</c:if> name="topic"
                                    value="after_camp_care">After Camp Care
                            </option>
                        </select>

                        <select name="applicationStatus">
                            <option name="applicationStatus" value="none">Application Status</option>
                            <option
                                    <c:if test="${subcategory.applicationStatus == 'accepted'}">selected</c:if>
                                    name="applicationStatus" value="accepted">Accepted
                            </option>
                            <option
                                    <c:if test="${subcategory.applicationStatus == 'rejected'}">selected</c:if>
                                    name="applicationStatus" value="rejected">Rejected
                            </option>
                            <option
                                    <c:if test="${subcategory.applicationStatus == 'confirmed'}">selected</c:if>
                                    name="applicationStatus" value="confirmed">Confirmed
                            </option>
                            <option
                                    <c:if test="${subcategory.applicationStatus == 'withdrawn'}">selected</c:if>
                                    name="applicationStatus" value="withdrawn">Withdrawn
                            </option>
                            <option
                                    <c:if test="${subcategory.applicationStatus == 'waitlisted'}">selected</c:if>
                                    name="applicationStatus" value="waitlisted">Waitlisted
                            </option>
                        </select>
                        <select name="mealPlan">
                            <option name="mealPlan" value="mealPlan">Meal Plan</option>
                            <option
                                    <c:if test="${subcategory.mealPlan == 'free_meals'}">selected</c:if> name="mealPlan"
                                    value="free_meals">Free Meals
                            </option>
                            <option
                                    <c:if test="${subcategory.mealPlan == 'reduced_meals'}">selected</c:if>
                                    name="mealPlan" value="reduced_meals">Reduced Meals
                            </option>
                            <option
                                    <c:if test="${subcategory.mealPlan == 'none'}">selected</c:if> name="mealPlan"
                                    value="none">None
                            </option>
                            <option
                                    <c:if test="${subcategory.mealPlan == 'prefer_not_to_enter'}">selected</c:if>
                                    name="mealPlan" value="prefer_not_to_enter">Prefer Not To Enter
                            </option>
                        </select>

                        <input type="submit" value="Submit">

                    </form>
                    <p id="results"></p>
                    <div id="data-container">
                        <div class="stat-container">
                            <h3>Ethnicities</h3>
                            <canvas id="ethnicityChart"></canvas>
                        </div>
                        <div class="stat-container">
                            <h3>Income</h3>
                            <canvas id="incomeChart"></canvas>
                        </div>
                        <div class="stat-container">
                            <h3>Gender</h3>
                            <canvas id="genderChart"></canvas>
                        </div>
                        <div class="stat-container">
                            <h3>Topics</h3>
                            <canvas id="topicChart"></canvas>
                        </div>
                        <div class="stat-container">
                            <h3>Grades</h3>
                            <canvas id="gradeChart"></canvas>
                        </div>
                        <div class="stat-container">
                            <h3>Meal Plans</h3>
                            <canvas id="mealPlanChart"></canvas>
                        </div>
                    </div>
                    <div>
                        <h3>Schools</h3>
                        <table class="schools-table">
                            <thead>
                            <th>School</th>
                            <th># of Students</th>
                            </thead>
                            <tbody>
                            <c:forEach items="${schoolStats}" var="schoolStat" varStatus="loop">
                                <tr>
                                    <td><c:out value="${schoolStat.getKey()}"/></td>
                                    <td><c:out value="${schoolStat.getValue()}"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <link rel="stylesheet"
                          href="https://cdnjs.cloudflare.com/ajax/libs/jquery-footable/3.1.6/footable.standalone.min.css"
                          integrity="sha512-xd7mB11XVgROqK55H8H6wqtT6tqizbdG1JctcfVxccY5e1eSao8yqhS+RQc6ps8+w67XdOtIWhqrwYCXr2iE4Q=="
                          crossorigin="anonymous"/>
                    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-footable/3.1.6/footable.min.js"
                            integrity="sha512-aVkYzM2YOmzQjeGEWEU35q7PkozW0vYwEXYi0Ko06oVC4NdNzALflDEyqMB5/wB4wH50DmizI1nLDxBE6swF3g=="
                            crossorigin="anonymous"></script>
                    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.js"
                            integrity="sha512-s+xg36jbIujB2S2VKfpGmlC3T5V2TF3lY48DX7u2r9XzGzgPsa6wTpOQA7J9iffvdeBN0q9tKzRxVxw1JviZPg=="
                            crossorigin="anonymous"></script>
                    <script>
                        const chartJSBackgroundColors = [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ];

                        const chartJSBorderColors = [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ];

                        const sumValues = obj => Object.values(obj).reduce((a, b) => a + b);

                        const
                            ethnicityData = {
                                <c:forEach items="${ethnicityStats}" var="ethnicityStat" varStatus="loop">"<c:out value="${ethnicityStat.getKey()}" escapeXml="false"/>": <c:out value="${ethnicityStat.getValue()}"/>, </c:forEach>
                            }
                        const ethnicityChartCtx = document.getElementById('ethnicityChart').getContext('2d');
                        const ethnicityChart = new Chart(ethnicityChartCtx, {
                            type: 'bar',
                            data: {
                                labels: Object.keys(ethnicityData),
                                datasets: [{
                                    label: '# of Students',
                                    data: Object.values(ethnicityData),
                                    backgroundColor: chartJSBackgroundColors,
                                    borderColor: chartJSBackgroundColors,
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                maintainAspectRatio: false,
                                title: {
                                    display: true,
                                    text: "Matched " + sumValues(ethnicityData) + " Students"
                                },
                                scales: {
                                    yAxes: [{
                                        ticks: {
                                            beginAtZero: true,
                                            precision: 0
                                        }
                                    }]
                                },
                                tooltips: {
                                    callbacks: {
                                        afterTitle: function (tooltipItems, data) {

                                            const sum = data.datasets[0].data.reduce((total, num) => {
                                                return total + num
                                            })

                                            const count = data.datasets[0].data[tooltipItems[0].index];

                                            return (100 * count / sum).toFixed(2) + "%"
                                        }
                                    }
                                }
                            }
                        });


                        const
                            incomeData = {
                                <c:forEach items="${incomeStats}" var="incomeStat" varStatus="loop">"<c:out value="${incomeStat.getKey()}" escapeXml="false"/>": <c:out value="${incomeStat.getValue()}"/>, </c:forEach>
                            }
                        const incomeChartCtx = document.getElementById('incomeChart').getContext('2d');
                        const incomeChart = new Chart(incomeChartCtx, {
                            type: 'doughnut',
                            data: {
                                labels: Object.keys(incomeData),
                                datasets: [{
                                    label: '# of Parents',
                                    data: Object.values(incomeData),
                                    backgroundColor: chartJSBackgroundColors,
                                    borderColor: chartJSBackgroundColors,
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                maintainAspectRatio: false,
                                title: {
                                    display: true,
                                    text: "Matched " + sumValues(incomeData) + " Parents"
                                },
                                // scales: {
                                //     yAxes: [{
                                //         ticks: {
                                //             beginAtZero: true,
                                //             precision: 0
                                //         }
                                //     }]
                                // }
                            }
                        });

                        const
                            genderData = {
                                <c:forEach items="${genderStats}" var="genderStat" varStatus="loop">"<c:out value="${genderStat.getKey()}" escapeXml="false"/>": <c:out value="${genderStat.getValue()}"/>, </c:forEach>
                            }
                        const genderChartCtx = document.getElementById('genderChart').getContext('2d');
                        const genderChart = new Chart(genderChartCtx, {
                            type: 'doughnut',
                            data: {
                                labels: Object.keys(genderData),
                                datasets: [{
                                    label: '# of Students',
                                    data: Object.values(genderData),
                                    backgroundColor: chartJSBackgroundColors,
                                    borderColor: chartJSBackgroundColors,
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                maintainAspectRatio: false,
                                title: {
                                    display: true,
                                    text: "Matched " + sumValues(genderData) + " Students"
                                },
                            }
                        });
                        const
                            topicData = {
                                <c:forEach items="${topicStats}" var="topicStat" varStatus="loop">"<c:out value="${topicStat.getKey()}" escapeXml="false"/>": <c:out value="${topicStat.getValue()}"/>, </c:forEach>
                            }
                        const topicChartCtx = document.getElementById('topicChart').getContext('2d');
                        const topicChart = new Chart(topicChartCtx, {
                            type: 'bar',
                            data: {
                                labels: Object.keys(topicData),
                                datasets: [{
                                    label: '# of Students',
                                    data: Object.values(topicData),
                                    backgroundColor: chartJSBackgroundColors,
                                    borderColor: chartJSBackgroundColors,
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                maintainAspectRatio: false,
                                title: {
                                    display: true,
                                    text: "Matched " + sumValues(topicData) + " Students"
                                },
                                scales: {
                                    yAxes: [{
                                        ticks: {
                                            beginAtZero: true,
                                            precision: 0
                                        }
                                    }]
                                }
                            }
                        });

                        const
                            gradeData = {
                                <c:forEach items="${gradeStats}" var="gradeStat" varStatus="loop">"<c:out value="${gradeStat.getKey()}" escapeXml="false"/>": <c:out value="${gradeStat.getValue()}"/>, </c:forEach>
                            }
                        const gradeChartCtx = document.getElementById('gradeChart').getContext('2d');
                        const gradeChart = new Chart(gradeChartCtx, {
                            type: 'bar',
                            data: {
                                labels: Object.keys(gradeData),
                                datasets: [{
                                    label: '# of Students',
                                    data: Object.values(gradeData),
                                    backgroundColor: chartJSBackgroundColors,
                                    borderColor: chartJSBackgroundColors,
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                maintainAspectRatio: false,
                                title: {
                                    display: true,
                                    text: "Matched " + sumValues(gradeData) + " Students"
                                },
                                scales: {
                                    yAxes: [{
                                        ticks: {
                                            beginAtZero: true,
                                            precision: 0
                                        }
                                    }]
                                }
                            }
                        });

                        const
                            mealPlanData = {
                                <c:forEach items="${mealPlanStats}" var="mealPlanStat" varStatus="loop">"<c:out value="${mealPlanStat.getKey()}" escapeXml="false"/>": <c:out value="${mealPlanStat.getValue()}"/>, </c:forEach>
                            }
                        const mealPlanCtx = document.getElementById('mealPlanChart').getContext('2d');
                        const mealPlanChart = new Chart(mealPlanCtx, {
                            type: 'bar',
                            data: {
                                labels: Object.keys(mealPlanData),
                                datasets: [{
                                    label: '# of Students',
                                    data: Object.values(mealPlanData),
                                    backgroundColor: chartJSBackgroundColors,
                                    borderColor: chartJSBackgroundColors,
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                maintainAspectRatio: false,
                                title: {
                                    display: true,
                                    text: "Matched " + sumValues(mealPlanData) + " Students"
                                },
                                scales: {
                                    yAxes: [{
                                        ticks: {
                                            beginAtZero: true,
                                            precision: 0
                                        }
                                    }]
                                }
                            }
                        })
                    </script>
                </div>
            </div>
        </div>
    </jsp:body>
</t:adminpage>
<!-- Ends: #CONTAINER -->