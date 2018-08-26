package it.italiaonline.rnd.time

import spock.lang.Specification
import spock.lang.Shared
import spock.lang.Unroll

/**
 * Given two fixed months in time returns a list with the series of
 * months between them (included)
 * 
 */
class TimeSeriesSpec extends Specification {

	@Shared
	Date            defaultStart       = Date.parse("yyyy-MM", '2016-04')
	@Shared
	Date            defaultEnd         = Date.parse("yyyy-MM", '2017-04')
	@Shared
	Date            nextTwoYears       = Date.parse('yyyy-MM', '2018-04')
	@Shared
	Date            nextMonth          = Date.parse('yyyy-MM','2016-05')
	@Shared
	Date            nextWeek           = Date.parse('yyyy-MM-dd', '2016-04-08')
	@Shared
	String          defaultFormat      = 'yyyy-MM'
	@Shared
	TimeGranularity defaultGranularity = TimeGranularity.MONTH

	def "Should not raise any exceptions if all the parametersa are valid ones"() {
		when:
			def timeSeries = new TimeSeries(defaultStart, defaultEnd, defaultFormat, defaultGranularity)
		then:
			noExceptionThrown()
	}

	@Unroll
	def "Should raise an exception if the a null parameter is passed"() {
		when:
			def timeSeries = new TimeSeries(
												startDate,
												endDate,
												format,
												granularity
											)
		then:
			thrown(NullPointerException)
		where: 
			startDate    | endDate    | format        | granularity
			null         | defaultEnd | defaultFormat | defaultGranularity
			defaultStart | null       | defaultFormat | defaultGranularity
			defaultStart | defaultEnd | null          | defaultGranularity
			defaultStart | defaultEnd | defaultFormat | null
	}

	@Unroll
	def "Should return the correct time series based on the input values"() {
		given:
			def timeSeries = new TimeSeries (
												start,
												end,
												format,
												granularity
											)
		expect:
			result == timeSeries.series()

		where:
			start        | end          | format        | granularity          || result
			defaultStart | nextTwoYears | 'y'           | TimeGranularity.YEAR || ['2016','2017','2018']
			defaultStart | defaultEnd   | defaultFormat | defaultGranularity   || ['2016-04','2016-05','2016-06','2016-07','2016-08','2016-09','2016-10','2016-11','2016-12','2017-01','2017-02','2017-03','2017-04']
			defaultStart | nextMonth    | 'y-MM-dd'     | TimeGranularity.WEEK || ['2016-04-01','2016-04-08','2016-04-15','2016-04-22','2016-04-29']
			defaultStart | nextWeek     | 'y-MM-dd'     | TimeGranularity.DAY  || ['2016-04-01','2016-04-02','2016-04-03','2016-04-04','2016-04-05','2016-04-06','2016-04-07','2016-04-08']
	}

}
