# generic-IOT-infrastructure

This project provides a generic infrastructure for supporting products that use “Internet Of Things” (IOT). 
Its goal is to allow manufacturing companies to receive IOT data from products sold to end users,
and to store, analyze and present the data. 
The IOT infrastructure is written in Java, communicates using HTTP and stores data in relational databases (e.g., MySQL).
However, the project is designed as a modular system that allow to changes its components with ease 
(e.g., change to NoSQL databases). Moreover, The system is designed to allow addition of new capabilities 
(e.g., logging) by plug & play, without the need for server-downtime.
