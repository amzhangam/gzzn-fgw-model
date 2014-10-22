/*
 * 	Character Count Plugin - jQuery plugin
 * 	Dynamic character count for text areas and input fields
 *	written by Alen Grakalic	
 *	http://cssglobe.com/post/7161/jquery-plugin-simplest-twitterlike-dynamic-character-count-for-textareas
 *
 *	Copyright (c) 2009 Alen Grakalic (http://cssglobe.com)
 *	Dual licensed under the MIT (MIT-LICENSE.txt)
 *	and GPL (GPL-LICENSE.txt) licenses.
 *
 *	Built for jQuery library
 *	http://jquery.com
 *
 */
 
(function($) {

	$.fn.charCount = function(options){
	  
		// default configuration properties
		var defaults = {	
			allowed: 140,		
			warning: 10,
			css:'counter',
			counterElement: 'span',
			cssWarning: 'warning',
			cssExceeded: 'exceeded',
			counterText: ''
		}; 
			
		var options = $.extend(defaults, options); 
		
		function calculate(obj){
			var count  = $(obj).val().replace(/[^\x00-\xff]/g, "**").length;
			var available = options.allowed - count;
			var theObj=$(obj).nextAll().last(options.counterElement);
			if(available <= options.warning && available >= 0){
				theObj.addClass(options.cssWarning);
			} else {
				theObj.removeClass(options.cssWarning);
			}
			if(available < 0){
				theObj.addClass(options.cssExceeded);
			} else {
				theObj.removeClass(options.cssExceeded);
			}
			var allowedStr="<font color='#000'>(</font>";
			allowedStr+=options.counterText + available;
			allowedStr+="<font color='#000'>/"+options.allowed+")</font>";
			theObj.html(allowedStr);
		};
				
		this.each(function() {  
			$(this).after('<'+ options.counterElement +' class="' + options.css + '">'+ options.counterText +'</'+ options.counterElement +'>');
			calculate(this);
			$(this).keyup(function(){calculate(this);});
			$(this).change(function(){calculate(this);});
		});
	  
	};

})(jQuery);
