// 期間別
const chartPeriod = document.getElementById("chartPeriod");
const periodLabels = [];
const firstHalfData = [];
const secondHalfData = [];

// 年ごとのデータを処理
for(let year in subscriptionDataMap) {
	if(subscriptionDataMap.hasOwnProperty(year)) {
		let counts = subscriptionDataMap[year];
		periodLabels.push(year + "年");
		// 支払い回数を売上に変換
		firstHalfData.push(counts.firstHalf * 300);
		secondHalfData.push(counts.secondHalf * 300);
	}
}

const Period = new Chart(chartPeriod, {
	type: "bar",
	data: {
		labels: periodLabels,
		datasets: [
			{
				label: "上半期",
				data: firstHalfData,
				backgroundColor: "rgba(219, 39, 91, 0.5)"
			},
			{
				label: "下半期",
				data: secondHalfData,
				backgroundColor: "rgba(219, 91, 39, 0.5)"
			}
		]
	},
	options: {
		responsive: true,
		scales: {
        	y: {
		        beginAtZero: true,
            	suggestedMax: 3000,
		        ticks: {
					stepSize: 100,
		            callback: function(value, index, values){
              			return  value +  "円"
            		}
        		}
        	}
    	}
	}
});

// 年代別
const chartGeneration = document.getElementById("chartGeneration");
// 定義済みのラベル順序に基づいてデータを並べる
const generationLabels = ["10代以下", "20代", "30代", "40代", "50代", "60代", "70歳以上", "未回答"];
const ageGroupKeys = ["10s and under", "20s", "30s", "40s", "50s", "60s", "70s and above", "Unknown"];

// subscriptionUserGenerationMapをMapオブジェクトに変換
const map = new Map(Object.entries(subscriptionUserGenerationMap));
console.log(subscriptionUserGenerationMap);

const generationList = ageGroupKeys.map(key => map.get(key) || 0);

const Generation = new Chart(chartGeneration, {
    type: "pie",
    data: {
        labels: generationLabels,
        datasets: [{
            backgroundColor: [
                "#ADD8E6", // 10代以下 - 淡いブルー
                "#FFD1DC", // 20代 - 淡いピンク
                "#FFB347", // 30代 - 淡いオレンジ
                "#B39EB5", // 40代 - 淡いパープル
                "#FFB6C1", // 50代 - 淡いライトピンク
                "#77DD77", // 60代 - 淡いグリーン
                "#D2B48C", // 70歳以上 - 淡い茶色
                "#CFCFC4" // 未回答 - 淡いグレー
            ],
            data: generationList
        }]
    },
    options: {
        plugins: {
            legend: {
                position: "right",
                align: "center"
            }
        }
    }
});