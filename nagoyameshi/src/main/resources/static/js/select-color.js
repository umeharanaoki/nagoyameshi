jQuery(function($){
  const Target = $('.nagoyameshi-select-color');
  // ページ読み込み時に選択された値をチェック	
  Target.each(function(){	
    if ($(this).val() !== "") {	
      $(this).removeClass('nagoyameshi-select-color');	
    }	
  });
  $(Target).on('change', function(){
    if ($(this).val() !== ""){
      $(this).removeClass('nagoyameshi-select-color');
    } else {
      $(this).addClass('nagoyameshi-select-color');
    }
  });
});