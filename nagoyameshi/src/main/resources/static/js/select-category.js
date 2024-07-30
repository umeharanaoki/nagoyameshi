
function selectCategory(button) {
	// 登録フォームを非表示にし、更新・削除フォームを表示する
	document.getElementById('categoryRegisterForm').style.display = 'none';
	document.getElementById('categoryEditForm').style.display = 'flex';
	
	// ボタンのデータ属性からカテゴリの情報を取得
	const categoryId = button.getAttribute('data-id');
	const categoryName = button.getAttribute('data-name');
	
	// 取得した値をフォームやモーダルに設定
	// 更新フォームにidを設定
	const categoryEditFormInputId = document.querySelector('#categoryEditForm input[name="id"]');
	categoryEditFormInputId.value = categoryId;
	// 更新フォームにカテゴリ名を設定
	const categoryEditFormInputName = document.querySelector('#categoryEditForm input[name="name"]');
	categoryEditFormInputName.value = categoryName;
	
	// モーダルの削除確認メッセージにカテゴリ名を反映
	const deleteMessage = document.getElementById('deleteCategoryMessage');
	deleteMessage.innerText = categoryName + 'を削除してもよろしいですか？';
	
	// 削除するカテゴリのidをurlに設定
    const categoryDeleteForm = document.getElementById('categoryDeleteForm');
    categoryDeleteForm.action = `/admin/categories/${categoryId}/delete`;
}

// 初期状態では登録フォームを表示し、更新・削除フォームを非表示にする
window.onload = function() {
	document.getElementById('categoryRegisterForm').style.display = 'block';
	document.getElementById('categoryEditForm').style.display = 'none';
}