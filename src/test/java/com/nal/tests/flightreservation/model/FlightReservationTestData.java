package com.nal.tests.flightreservation.model;

/**
 * Created by N on 2/14/2026
 **/

public record FlightReservationTestData(String firstName,
                                        String lastName,
                                        String email,
                                        String password,
                                        String street,
                                        String city,
                                        String zip,
                                        String passengersCount,
                                        String expectedPrice) { }
