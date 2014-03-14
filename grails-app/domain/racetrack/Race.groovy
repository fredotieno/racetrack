package racetrack

class Race {
    static constraints = {
        name(blank:false, maxSize:50)
	startDate(validator: {return(it > new Date())})
	city()
	state(inList:["GA", "NC", "SC", "VA"])
	distance(range:0..10)
	cost(min:0.0, max:100.0)
	maxRunners(min:0, max:100000)
    }
    String name
    Date startDate
    String city
    String state
    BigDecimal distance
    BigDecimal cost
    Integer maxRunners

    BigDecimal inMiles(){
	return distance * 0.6214
    }
    static mappings = {
	sort "startDate"
    }

    static hasMany = [registrations:Registration]

    String toString(){
	return "${name}, ${startDate.format('MM/dd/yyyy')}"
    }
}
