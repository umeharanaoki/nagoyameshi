// お気に入りボタンがクリックされたときの処理
function toggleFavorite(restaurantId) {
	// console.log("Hello");
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		if(header && token) {
		xhr.setRequestHeader(header, token);
		}
	});
	// URLを動的に構築
	var url = "/restaurants/" + restaurantId + "/favorites/toggle";
    $.ajax({
        type: "POST",
        url: url,
        data: {}
        }).done(function(data) {
			// Favoriteのstatusが1（お気に入り追加済み）のとき
			if(data === 1) {
				updateFavoriteButton("♥お気に入り解除");
			} else {
				updateFavoriteButton("♡お気に入り追加");
			}
        });
    
    // お気に入りステータスを表示するメソッド
	function updateFavoriteButton(status) {
    // 画面上の適切な要素に表示
    document.getElementById("favoriteStatus").innerText = status;
	}
}