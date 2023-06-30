package model

import kotlinx.cinterop.internal.CStruct
import kotlinx.serialization.Serializable

object DATA {
    val jsonString = """
        {
          "locations": [
            {
              "id": 6217618,
              "name": "Recherche",
              "state": {
                "name": "Tasmania"
              },
              "zipCode": {
                "name": "7109"
              },
              "uri": "recherche-southern-tasmania",
              "slug": "recherche-tas-7109",
              "type": 2
            },
            {
              "id": 5714454,
              "name": "Red Cliffs",
              "state": {
                "name": "Victoria"
              },
              "zipCode": {
                "name": "3496"
              },
              "uri": "red-cliffs-mildura-mallee-victoria",
              "slug": "red-cliffs-vic-3496",
              "type": 2
            },
            {
              "id": 6217620,
              "name": "Red Creek",
              "state": {
                "name": "South Australia"
              },
              "zipCode": {
                "name": "5255"
              },
              "uri": "red-creek-outer-adelaide-south-australia",
              "slug": "red-creek-sa-5255",
              "type": 2
            },
            {
              "id": 6217621,
              "name": "Red Gully",
              "state": {
                "name": "Western Australia"
              },
              "zipCode": {
                "name": "6503"
              },
              "uri": "red-gully-midlands-western-australia",
              "slug": "red-gully-wa-6503",
              "type": 2
            },
            {
              "id": 6217622,
              "name": "Red Head",
              "state": {
                "name": "New South Wales"
              },
              "zipCode": {
                "name": "2430"
              },
              "uri": "red-head-mid-north-coast-new-south-wales",
              "slug": "red-head-nsw-2430",
              "type": 2
            },
            {
              "id": 5719102,
              "name": "Red Hill",
              "state": {
                "name": "Australian Capital Territory"
              },
              "zipCode": {
                "name": "2603"
              },
              "uri": "red-hill-canberra-canberra-greater-australian-capital-territory",
              "slug": "red-hill-act-2603",
              "type": 2
            },
            {
              "id": 6219435,
              "name": "Red Hill",
              "state": {
                "name": "New South Wales"
              },
              "zipCode": {
                "name": "2347"
              },
              "uri": "red-hill-new-south-wales",
              "slug": "red-hill-nsw-2347",
              "type": 2
            },
            {
              "id": 6219436,
              "name": "Red Hill",
              "state": {
                "name": "New South Wales"
              },
              "zipCode": {
                "name": "2720"
              },
              "uri": "red-hill-new-south-wales-1",
              "slug": "red-hill-nsw-2720",
              "type": 2
            }
          ],
          "totalResults": 103
        }
    """.trimIndent()
}

@Serializable
@CStruct("search_result")
data class SearchResult(
    val locations: List<Location>,
    val totalResults: Long
) {
    val locations_string: String
        get() = locations.toString()
}

@Serializable
@CStruct("location")
data class Location (
    val id: Long,
    val name: String,
    val state: State,
    val zipCode: State,
    val uri: String,
    val slug: String,
    val type: Long
)

@Serializable
@CStruct("state")
data class State (
    val name: String
)
