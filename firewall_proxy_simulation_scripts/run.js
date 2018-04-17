#!/usr/bin/env node

var loginserverport=8000;
var http = require('http');
var express  = require('express');
var app      = express();
var bodyParser = require('body-parser');
app.use(bodyParser.json()); 
app.use(bodyParser.urlencoded({ extended: true })); 
var httpProxy = require('http-proxy');
var apiProxy = httpProxy.createProxyServer();
var serverTomcat= 'http://localhost:8080',
	    serverLogin= 'http://localhost:'+loginserverport;
 
var ivuser="";

http.createServer(function (req, res) {
  var action=req.url;
  if(action.match("^/c/portal/logout"))action="/";
  if(action.match("^/login"))action="/";
  console.log("action="+action);
  res.write('<!DOCTYPE html><html><body><h2>YOU NEED TO LOG IN</h2><form method="POST" action="'+action+'">USERNAME:<br><input type="text" name="userName"><br><input type="submit" value="Submit"></form></body></html>');
  res.end(); 
}).listen(loginserverport);

app.all("/login*", function(req, res) {
	apiProxy.web(req, res, {target: serverLogin});
});
app.all("/logout*", function(req, res) {
	ivuser="";
	res.redirect("/c/portal/logout");
});

app.all("/*", function(req, res) {
	if(req.body.userName)ivuser=req.body.userName;
	if(ivuser){
		req.headers['iv-user']=ivuser;
		console.log('we try to get through as '+ivuser);
		apiProxy.web(req, res, {target: serverTomcat});
	}else{
		console.log('redirecting to Login Server');
		apiProxy.web(req, res, {target: serverLogin});
	}

});

app.listen(9090);
