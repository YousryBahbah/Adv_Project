# E-Commerce Backend — JSP & Servlets
A backend-focused e-commerce web application I built as part of a backend development course. 
The goal was to get hands-on with how real web backends are structured — request handling, 
authentication, database access, and caching — without relying on a heavy framework.

## What it does
- Browse a product catalog with a review/feedback section
- Sign up and log in with session-based and JWT-based authentication
- Admin accounts can add and delete products
- Rate limiting prevents users from spamming requests
- Product data is cached in Redis to avoid hitting the database every time

## Tech stack
- Java Servlets + JSP — request handling and server-side rendering
- MySQL — stores users, products, and reviews
- Redis — product caching and rate limiting
- JWT (Auth0 java-jwt) — stateless token authentication
