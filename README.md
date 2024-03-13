# WebPageManagementSystem

## graphql Example
```
query showQuery($titleFilter: String){
  shows(titleFilter: $titleFilter) {
        title
        releaseYear
    }
}

{
  "titleFilter": "is"
}
```