package it.italiaonline.rnd.time

import groovy.time.TimeCategory

class TimeSeries {
	Date            start
	Date            end
	String          format
	TimeGranularity granularity

	TimeSeries(
		Date start,
		Date end,
		String fmat,
		TimeGranularity tg
	) {
		this.start = Objects.requireNonNull(start).clearTime()
		this.end = Objects.requireNonNull(end).clearTime()
		this.format = Objects.requireNonNull(fmat)
		this.granularity = Objects.requireNonNull(tg)
	}

	List series() {
		def result = []
		def timeLapse = this.granularity.name().toLowerCase()
		use(TimeCategory) {
			for(def cursor = start; cursor <= end; cursor += 1."${timeLapse}") {
				result << cursor.format(this.format)
			}
		}
		result
	}
}
