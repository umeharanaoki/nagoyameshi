// 小数点以下第一位が0のとき.0まで表示する
document.getElementById('reviewScoreInput').addEventListener('input', function (e) {
    var value = e.target.value;
    if (value.includes('.') && value.split('.')[1].length === 1 && value.split('.')[1] === '0') {
        e.target.value = parseFloat(value).toFixed(1);
    }
});

// ユーザーが整数を入力したとき、小数点第一位まで表示する
document.getElementById('reviewScoreInput').addEventListener('blur', function (e) {
    var value = e.target.value;
    if (!value.includes('.')) {
        e.target.value = parseFloat(value).toFixed(1);
    }
});
