const TableRow = ({ row, columns }) => {
  return (
    <tr className="hover:bg-blue-50 transition">
      {columns.map((col) => (
        <td key={col.key} className="px-5 py-4 text-gray-700">
          {col.render ? col.render(row) : row[col.key]}
        </td>
      ))}
    </tr>
  );
};

export default TableRow;
