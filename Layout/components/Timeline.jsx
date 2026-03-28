import Badge from "../components/Badge";

const STATUS_COLOR = {
  OPEN: "yellow",
  IN_PROGRESS: "blue",
  RESOLVED: "green",
  REJECTED: "red",
};

const ComplaintTimeline = ({ events = [] }) => {
  return (
    <div className="space-y-6">
      {events.map((event, index) => (
        <div key={index} className="flex gap-6">
          {/* Timeline Indicator */}
          <div className="flex flex-col items-center">
            <div className="w-4 h-4 rounded-full bg-blue-600 mt-1" />
            {index !== events.length - 1 && (
              <div className="flex-1 w-px bg-blue-200 mt-2" />
            )}
          </div>

          {/* Content */}
          <div className="flex-1 bg-blue-50 border border-blue-100
                          rounded-xl p-5">
            <div className="flex flex-wrap items-center gap-3 mb-2">
              <Badge
                label={event.status}
                color={STATUS_COLOR[event.status]}
              />

              <span className="text-sm text-gray-600">
                {event.date}
              </span>

              <span className="text-sm text-gray-500">
                by <strong>{event.updatedBy}</strong>
              </span>
            </div>

            {event.comment && (
              <p className="text-gray-700 leading-relaxed">
                {event.comment}
              </p>
            )}
          </div>
        </div>
      ))}
    </div>
  );
};

export default ComplaintTimeline;
