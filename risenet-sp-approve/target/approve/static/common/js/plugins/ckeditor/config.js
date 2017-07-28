/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	// config.toolbar = 'Basic';
	config.allowedContent = true ;
	config.enterMode = CKEDITOR.ENTER_BR;
	config.shiftEnterMode = CKEDITOR.ENTER_P;
	config.removePlugins = 'save';
	//for editor freemarker template
	//config.protectedSource.push( /<\s*#include[\s\S]*?>/gi ) ; // <#include> tags.
	//config.protectedSource.push( /<\s*@html[\s\S]*?\/@html\s*>/gi ) ; // <@html></@html> tags.
	//config.protectedSource.push( /<\s*@head[\s\S]*?\/@head\s*>/gi ) ; // <@head></@head> tags.
	//config.protectedSource.push( /<\s*@body[\s\S]*?\/@body\s*>/gi ) ; // <@body></@body> tags.
	//config.protectedSource.push( /<\s*#list[\s\S]*?\/#list\s*>/gi ) ; // <@html></@html> tags.
	config.protectedSource.push( /'/g ) ; // &
	config.protectedSource.push( /&/g ) ; // '
};
