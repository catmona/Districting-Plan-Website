package com.mavericks.server.service;

import com.mavericks.server.dto.StateDTO;
import com.mavericks.server.dto.Point;
import com.mavericks.server.entity.State;
import com.mavericks.server.repository.DistrictingRepository;
import com.mavericks.server.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DistrictingService {

    @Autowired
    private DistrictingRepository distRepo;

    public StateDTO getStateSummary(String stateAbbreviation) {
        if (stateAbbreviation == "WA") {

        }

        "washington": [
        {
            "id": 1,
                "population": 791545,
                "democrat": 249944,
                "republican": 176407,
                "africanamerican": 12178,
                "white": 594230,
                "asianamerican": 102923,
                "hispanic": 76815
        },
        {
            "id": 2,
                "population": 760064,
                "democrat": 255252,
                "republican": 148384,
                "africanamerican": 25095,
                "white": 578055,
                "asianamerican": 72330,
                "hispanic": 84560
        },
        {
            "id": 3,
                "population": 756675,
                "democrat": 181347,
                "republican": 235579,
                "africanamerican": 12360,
                "white": 650254,
                "asianamerican": 24586,
                "hispanic": 77109
        },
        {
            "id": 4,
                "population": 735797,
                "democrat": 102667,
                "republican": 202108,
                "africanamerican": 10104,
                "white": 553564,
                "asianamerican": 10032,
                "hispanic": 291924
        },
        {
            "id": 5,
                "population": 734322,
                "democrat": 155737,
                "republican": 247815,
                "africanamerican": 12783,
                "white": 641200,
                "asianamerican": 18104,
                "hispanic": 50208
        },
        {
            "id": 6,
                "population": 726540,
                "democrat": 247429,
                "republican": 168783,
                "africanamerican": 29054,
                "white": 587323,
                "asianamerican": 27394,
                "hispanic": 58095
        },
        {
            "id": 7,
                "population": 817787,
                "democrat": 387109,
                "republican": 78240,
                "africanamerican": 41038,
                "white": 569986,
                "asianamerican": 119852,
                "hispanic": 66032
        },
        {
            "id": 8,
                "population": 770177,
                "democrat": 213123,
                "republican": 198423,
                "africanamerican": 41038,
                "white": 557905,
                "asianamerican": 73610,
                "hispanic": 92771
        },
        {
            "id": 9,
                "population": 751595,
                "democrat": 258771,
                "republican": 89697,
                "africanamerican": 41038,
                "white": 369451,
                "asianamerican": 179828,
                "hispanic": 97137
        },
        {
            "id": 10,
                "population": 770391,
                "democrat": 288977,
                "republican": 51430,
                "africanamerican": 49594,
                "white": 549123,
                "asianamerican": 56308,
                "hispanic": 97072
        }
  ],
        "nevada": [
        {
            "id": 1,
                "population": 723705,
                "democrat": 137868,
                "republican": 74490,
                "africanamerican": 88601,
                "white": 347382,
                "asianamerican": 60582,
                "hispanic": 343864
        },
        {
            "id": 2,
                "population": 766064,
                "democrat": 155780,
                "republican": 216078,
                "africanamerican": 16972,
                "white": 582218,
                "asianamerican": 32304,
                "hispanic": 183123
        },
        {
            "id": 3,
                "population": 853240,
                "democrat": 203421,
                "republican": 190975,
                "africanamerican": 72744,
                "white": 523798,
                "asianamerican": 126960,
                "hispanic": 166285
        },
        {
            "id": 4,
                "population": 786739,
                "democrat": 168457,
                "republican": 152284,
                "africanamerican": 131912,
                "white": 435644,
                "asianam/*erican": 47066,
                "hispanic": 238360
        }
  ],
        "arkansas": [
        {
            "id": 1,
                "population": 719048,
                "democrat": 106325,
                "republican": 237596,
                "africanamerican": 126465,
                "white": 556680,
                "asianamerican": 3775,
                "hispanic": 24421
        },
        {
            "id": 2,
                "population": 767662,
                "democrat": 148410,
                "republican": 184093,
                "africanamerican": 178792,
                "white": 543432,
                "asianamerican": 11921,
                "hispanic": 41445
        },
        {
            "id": 3,
                "population": 829149,
                "democrat": 106325,
                "republican": 214960,
                "africanamerican": 25683,
                "white": 695183,
                "asianamerican": 25356,
                "hispanic": 123805
        },
        {
            "id": 4,
                "population": 701945,
                "democrat": 75750,
                "republican": 191617,
                "africanamerican": 136528,
                "white": 519725,
                "asianamerican": 5026,
                "hispanic": 42280
        }
  ]
    }
}
