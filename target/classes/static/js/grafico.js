google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

// Draw the chart and set the chart values
function drawChart() {
  var data = google.visualization.arrayToDataTable([
  ['Task', 'Match'],
  ['Ganadas', parseInt($('#w').val())],
  ['Perdidas',  parseInt($('#l').val())],
  ['Empate', parseInt($('#d').val())]
]);

  // Optional; add a title and set the width and height of the chart
  var options = {'title':'Resultados de usuario', 'width':550, 'height':400};

  // Display the chart inside the <div> element with id="piechart"
  var chart = new google.visualization.PieChart(document.getElementById('piechart'));
  chart.draw(data, options);
}
