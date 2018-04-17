Very simple auto login example to demonstrate how the servlet filters and the auto-loging in liferay work.

It comes with a nodejs server that intercepts http calls to liferay and injects an "iv-user" into the http headers, the same way webseal does.

The autologin module is added to the Liferay authentication pipeline, loging the user as the user the "iv-user" header indicates.



How to use it:

Start the nodejs firewall by going to firewall_proxy_simulation_scripts and doing:



npm install

and 

./run.js



You will have a proxy that will intercept your http requests to liferay and will force you to log in.


Deploy your autoLogin portlet in Liferay
