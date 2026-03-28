import TableRow from "./TableRow";

const TableComponent = ({ columns = [], data = [] }) => {
  return (
    <div className="overflow-x-auto bg-white rounded-xl shadow-md border border-blue-100">
      <table className="min-w-full text-sm">
        <thead className="bg-blue-50 border-b border-blue-100">
          <tr>
            {columns.map((col) => (
              <th
                key={col.key}
                className="px-5 py-3 text-left font-semibold text-blue-900 uppercase tracking-wide"
              >
                {col.label}
              </th>
            ))}
          </tr>
        </thead>

        <tbody className="divide-y divide-blue-50">
          {data.length > 0 ? (
            data.map((row) => (
              <TableRow
                key={row.id ?? JSON.stringify(row)}
                row={row}
                columns={columns}
              />
            ))
          ) : (
            <tr>
              <td
                colSpan={columns.length}
                className="px-5 py-6 text-center text-blue-400"
              >
                No records found
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default TableComponent;
