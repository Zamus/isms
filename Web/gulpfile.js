﻿'use strict';

const gulp = require('gulp');
const concat = require('gulp-concat');
const ngHtml2Js = require("gulp-ng-html2js");
const sass = require('gulp-sass');

const dist = './WebContent/dist/';
const app = './app/';
const modules = './node_modules/';
const tomcatdist = '/home/pfi/tomcat/wtpwebapps/Web/dist';

gulp.task('default', ['build']);

gulp.task('build', ['sass', 'concat-templates', 'concat-vendor', 'concat-app']);

gulp.task('concat-app', function () {
	return standardProcessor([app + '**/*.module.js', app + '**/*.js'], 'app.js');
});

gulp.task('concat-vendor', function () {
	return standardProcessor([
			modules + 'angular/angular.js',
			modules + 'angular-ui-router/release/angular-ui-router.js',
			modules + 'chart.js/dist/Chart.js',
			modules + 'angular-chart.js/dist/angular-chart.js',
            modules + 'angular-websocket/dist/angular-websocket.js'], 'vendor.js');
});

gulp.task('concat-templates', function () {
	return standardProcessor([app + '**/*.html'], 'templates.js', ngHtml2Js({
		moduleName: 'isms.templates',
		stripPrefix: 'modules/'
	}));
});

gulp.task('sass', function () {
	return standardProcessor([app + 'styles/main.scss'], 'style.css', sass());
});

function standardProcessor(src, output, processor) {
	let line = gulp.src(src);
	if (processor) {
		line = line.pipe(processor);
	}
	return line.pipe(concat(output)).pipe(gulp.dest(dist)).pipe(gulp.dest(tomcatdist));
}