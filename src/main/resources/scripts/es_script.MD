
## version:5.1.2 
## /_scripts/painless/script_name
## painless

```json
{
    "script":"double score = 0;if (doc['csId'].value != null && params.csId !=null){if(params.csId.stream().map(num -> (int) num).collect(Collectors.toList()).contains((int)doc['csId'].value)){ score += 100;}}if (doc['registrationDate'].value != null && params.fromRegistrationDate !=null && params.toRegistrationDate!=null && doc['registrationDate'].value > params.fromRegistrationDate && doc['registrationDate'].value < params.toRegistrationDate) {score += 90;}if (doc['quotedPrice'].value != null && params.fromQuotedPrice!=null && params.toQuotedPrice!=null && doc['quotedPrice'].value >= params.fromQuotedPrice && doc['quotedPrice'].value <= params.toQuotedPrice) {score += 200;}return score;"
}
```

```json
{
    "script":"if(doc['effectiveOrderExpireTime'].value != null && doc['effectiveOrderExpireTime'].value!=0){def start = Instant.ofEpochMilli(params.now).atZone(ZoneOffset.ofHours(8)).toLocalDate();def end = Instant.ofEpochMilli(doc['effectiveOrderExpireTime'].value).atZone(ZoneOffset.ofHours(8)).toLocalDate();return start.until(end, ChronoUnit.DAYS) < params.gtDay;}else {return false;}"
}
```

```json
{
    "script":"def end = Instant.ofEpochMilli(params.now).atZone(ZoneOffset.ofHours(8)).toLocalDate();def start = Instant.ofEpochMilli(doc['registrationDate'].value).atZone(ZoneOffset.ofHours(8)).toLocalDate();return start.until(end, ChronoUnit.MONTHS);"
}
```
